/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acclivetiming.Monitor.extensions.incidents;

import acclivetiming.Monitor.client.SessionId;
import acclivetiming.Monitor.client.events.AfterPacketReceived;
import acclivetiming.Monitor.client.events.BroadcastingEventEvent;
import acclivetiming.Monitor.eventbus.Event;
import acclivetiming.Monitor.eventbus.EventBus;
import acclivetiming.Monitor.eventbus.EventListener;
import acclivetiming.Monitor.extensions.AccClientExtension;
import acclivetiming.Monitor.extensions.logging.LoggingExtension;
import acclivetiming.Monitor.networking.data.AccBroadcastingData;
import acclivetiming.Monitor.networking.data.BroadcastingEvent;
import acclivetiming.Monitor.networking.enums.BroadcastingEventType;
import acclivetiming.Monitor.utility.TimeUtils;
import acclivetiming.Monitor.extensions.incidents.events.Accident;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author Leonard
 */
public class IncidentExtension
        extends AccClientExtension
        implements EventListener {

    /**
     * This classes logger.
     */
    private static Logger LOG = Logger.getLogger(IncidentExtension.class.getName());

    /**
     * Incident counter for the different sessions.
     */
    private static Map<SessionId, Integer> incidentCounter = new HashMap<>();

    /**
     * Last accident that is waiting to be commited.
     */
    private IncidentInfo stagedAccident = null;
    /**
     * List of accidents that have happened.
     */
    private List<IncidentInfo> accidents = new LinkedList<>();
    /**
     * Timestamp for when the race session started.
     */
    private long raceStartTimestamp;
    /**
     * Table model for the incident panel table.
     */
    private IncidentTableModel model = new IncidentTableModel();

    public IncidentExtension() {
        this.panel = new IncidentPanel(this);
        EventBus.register(this);
    }

    public AccBroadcastingData getModel() {
        return client.getModel();
    }

    public IncidentTableModel getTableModel() {
        return model;
    }

    public List<IncidentInfo> getAccidents() {
        List<IncidentInfo> a = new LinkedList<>(accidents);
        Collections.reverse(a);
        return Collections.unmodifiableList(a);
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof AfterPacketReceived) {
            afterPacketReceived(((AfterPacketReceived) e).getType());
        } else if (e instanceof BroadcastingEventEvent) {
            BroadcastingEvent event = ((BroadcastingEventEvent) e).getEvent();
            if (event.getType() == BroadcastingEventType.ACCIDENT) {
                onAccident(event);
            }
        }
    }

    public void afterPacketReceived(byte type) {
        if (stagedAccident != null) {
            long now = System.currentTimeMillis();
            if (now - stagedAccident.getTimestamp() > 1000) {
                commitAccident(stagedAccident);
                stagedAccident = null;
            }
        }
    }

    public void onAccident(BroadcastingEvent event) {
        String logMessage = "Accident: #" + client.getModel().getCar(event.getCarId()).getCarNumber()
                + "\t" + TimeUtils.asDuration(client.getModel().getSessionInfo().getSessionTime());
        LoggingExtension.log(logMessage);
        LOG.info(logMessage);

        float sessionTime = client.getModel().getSessionInfo().getSessionTime();
        SessionId sessionId = client.getSessionId();
        if (stagedAccident == null) {
            stagedAccident = new IncidentInfo(sessionTime,
                    client.getModel().getCar(event.getCarId()),
                    sessionId);
        } else {
            float timeDif = stagedAccident.getLatestTime() - sessionTime;
            if (timeDif > 1000) {
                commitAccident(stagedAccident);
                stagedAccident = new IncidentInfo(sessionTime,
                        client.getModel().getCar(event.getCarId()),
                        sessionId);
            } else {
                stagedAccident = stagedAccident.addCar(sessionTime,
                        client.getModel().getCar(event.getCarId()),
                        System.currentTimeMillis());
            }
        }
    }

    public void addEmptyAccident() {
        commitAccident(new IncidentInfo(client.getModel().getSessionInfo().getSessionTime(),
                client.getSessionId()));
    }

    private void commitAccident(IncidentInfo a) {
        List<IncidentInfo> newAccidents = new LinkedList<>();
        newAccidents.addAll(accidents);
        newAccidents.add(a.withIncidentNumber(getAndIncrementCounter(client.getSessionId())));
        accidents = newAccidents;
        model.setAccidents(accidents);

        EventBus.publish(new Accident(a));
    }

    private int getAndIncrementCounter(SessionId sessionId) {
        int result = incidentCounter.getOrDefault(sessionId, 0);
        incidentCounter.put(sessionId, result + 1);
        return result;
    }

}
