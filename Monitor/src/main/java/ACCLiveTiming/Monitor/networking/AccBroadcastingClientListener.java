/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acclivetiming.Monitor.networking;

import acclivetiming.Monitor.networking.data.BroadcastingEvent;
import acclivetiming.Monitor.networking.data.CarInfo;
import acclivetiming.Monitor.networking.data.RealtimeInfo;
import acclivetiming.Monitor.networking.data.SessionInfo;
import acclivetiming.Monitor.networking.data.TrackInfo;
import java.util.List;

/**
 *
 * @author Leonard
 */
public interface AccBroadcastingClientListener {

    public void onRegistrationResult(int connectionID, boolean success, boolean readOnly, String message);

    public void onRealtimeUpdate(SessionInfo sessionInfo);

    public void onRealtimeCarUpdate(RealtimeInfo info);

    public void onEntryListUpdate(List<Integer> carIds);

    public void onTrackData(TrackInfo info);

    public void onEntryListCarUpdate(CarInfo carInfo);

    public void onBroadcastingEvent(BroadcastingEvent event);

    public void afterPacketReceived(byte type);
}
