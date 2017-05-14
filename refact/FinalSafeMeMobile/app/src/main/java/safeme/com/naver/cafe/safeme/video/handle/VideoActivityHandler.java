package safeme.com.naver.cafe.safeme.video.handle;

import android.os.Handler;
import android.os.Message;

import safeme.com.naver.cafe.safeme.constants.Constants;

/**
 * Created by dasom on 2017-05-14.
 */
public class VideoActivityHandler extends Handler {
    private VideoActivityHandleCallback handleCallback;

    public VideoActivityHandler(VideoActivityHandleCallback handleCallback) {
        this.handleCallback = handleCallback;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case Constants.ON_LOCATION_CHANGED:
                handleCallback.onLocationChanged();
                break;

            case Constants.STREAM_ERROR:
                handleCallback.onStreamError();
                break;

            case Constants.ON_FINISHED_RECORD_VIDEO:
                handleCallback.onFinishedRecordVideo();
                break;

            case Constants.ON_FINISHED_SEND_VIDEO:
                handleCallback.onFinishedSendVideo();
                break;

            case Constants.FINISH_VIDEO_ACTIVITY:
                handleCallback.onFinishActivity();
                break;
        }
    }
}