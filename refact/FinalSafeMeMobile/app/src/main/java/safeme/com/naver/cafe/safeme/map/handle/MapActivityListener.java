package safeme.com.naver.cafe.safeme.map.handle;

import android.os.Message;

/**
 * Created by dasom on 2017-05-21.
 */
public interface MapActivityListener {
    void onRecievedPolicePositions(Message message);
    void onRecievedFootPositions(Message message);
    void onParsingError(Message message);
    void oRemovedMarker(Message message);
}
