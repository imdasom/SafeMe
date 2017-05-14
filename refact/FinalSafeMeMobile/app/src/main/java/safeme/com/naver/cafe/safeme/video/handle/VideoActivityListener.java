package safeme.com.naver.cafe.safeme.video.handle;

import android.app.Activity;

/**
 * Created by dasom on 2017-05-14.
 */
public interface VideoActivityListener {
    void setActivity(Activity activity);
    void onLocationChanged();
    void onFinishedRecordVideo();
    void onStreamError();
    void onFinishedSendVideo();
    void onFinishActivity();
}
