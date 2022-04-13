/*
 * Copyright (c) 2021 Leonard Sch�ngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.gui.app.autobroadcast;

import java.util.List;
import java.util.stream.Collectors;
import racecontrol.client.AccBroadcastingClient;
import racecontrol.client.events.RealtimeUpdateEvent;
import racecontrol.client.extension.autobroadcast.AutobroadcastExtension;
import racecontrol.client.protocol.SessionInfo;
import racecontrol.eventbus.Event;
import racecontrol.eventbus.EventBus;
import racecontrol.eventbus.EventListener;
import racecontrol.gui.RaceControlApplet;
import static racecontrol.gui.RaceControlApplet.getApplet;
import racecontrol.gui.app.Menu;
import racecontrol.gui.app.PageController;
import racecontrol.gui.lpui.LPContainer;
import racecontrol.utility.TimeUtils;

/**
 *
 * @author Leonard
 */
public class AutobroadcastController
        implements EventListener, PageController {

    private final AccBroadcastingClient client;
    /**
     * Reference to the extension.
     */
    private final AutobroadcastExtension extension;
    /**
     * The panel for this controller.
     */
    private final AutobroadcastPanel panel;
    /**
     * The table model for the rating table.
     */
    private final RatingTableModel tableModel;
    /**
     * Menu item.
     */
    private final Menu.MenuItem menuItem;

    public AutobroadcastController() {
        EventBus.register(this);

        menuItem = new Menu.MenuItem("Auto Cam",
                getApplet().loadResourceAsPImage("/images/RC_Menu_AutoBroadcast.png"));

        client = AccBroadcastingClient.getClient();
        extension = AutobroadcastExtension.getInstance();

        panel = new AutobroadcastPanel();
        tableModel = new RatingTableModel();

        panel.ratingTable.setTableModel(tableModel);
        panel.ratingTable.setCellClickAction(this::onCellClickAction);

        panel.enableCheckBox.setChangeAction(this::enableCheckboxChanged);
    }

    @Override
    public Menu.MenuItem getMenuItem() {
        return menuItem;
    }

    @Override
    public LPContainer getPanel() {
        return panel;
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof RealtimeUpdateEvent) {
            RaceControlApplet.runLater(() -> {
                tableModel.setEntriesNew(extension.getEntries());
                if (panel.sortByRatingCheckBox.isSelected()) {
                    tableModel.sortRating();
                } else {
                    tableModel.sortPosition();
                }
                panel.ratingTable.invalidate();

                SessionInfo info = ((RealtimeUpdateEvent) e).getSessionInfo();
                panel.currentCamera.setTextFixed(info.getActiveCameraSet() + " " + info.getActiveCamera());

                long countDown = extension.getNextCamChange() - System.currentTimeMillis();
                panel.nextCameraCountdown.setTextFixed("Next cam in: " + TimeUtils.asDelta((int) countDown) + "s");

                List<Long> camScreenTimes = extension.getCamScreenTime();
                final long totalScreenTime = camScreenTimes.get(0)
                        + camScreenTimes.get(1)
                        + camScreenTimes.get(2)
                        + camScreenTimes.get(3);
                String percents = extension.getCamScreenTime().stream()
                        .map(l -> String.format("%.1f%%", l * 100f / totalScreenTime))
                        .collect(Collectors.joining(", "));
                panel.cameraScreenTime.setTextFixed("screen time share: " + percents);
            });
        }
    }

    private void onCellClickAction(int column, int row) {
        if (row >= tableModel.getRowCount()) {
            return;
        }
        client.sendChangeFocusRequest(
                tableModel.getEntryNew(row).car.id);
    }

    private void enableCheckboxChanged(boolean state) {
        AutobroadcastExtension.getInstance().setEnabled(state);
    }

}
