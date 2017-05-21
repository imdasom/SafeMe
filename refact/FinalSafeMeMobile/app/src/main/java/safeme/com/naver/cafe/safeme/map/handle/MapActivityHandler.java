package safeme.com.naver.cafe.safeme.map.handle;

import android.os.Handler;
import android.os.Message;

import safeme.com.naver.cafe.safeme.constants.Constants;

/**
 * Created by dasom on 2017-05-21.
 */
public class MapActivityHandler extends Handler {
    private MapActivityHandleCallback handleCallback;

    public MapActivityHandler(MapActivityHandleCallback handleCallback) {
        this.handleCallback = handleCallback;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case Constants.GET_POLICEPOS_DATA:
                handleCallback.onRecievedPolicePositions();
                break;

            case Constants.GET_FOOTPOS_DATA:
                handleCallback.onRecievedFootPositions();
                break;

            case Constants.PARSING_ERROR:
                handleCallback.onParsingError();
                break;

            case Constants.REMOVE_MARKER:
                handleCallback.oRemovedMarker();
                break;
        }
    }
}
