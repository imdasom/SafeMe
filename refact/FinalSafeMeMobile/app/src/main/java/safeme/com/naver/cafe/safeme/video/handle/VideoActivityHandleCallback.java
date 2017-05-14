package safeme.com.naver.cafe.safeme.video.handle;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import safeme.com.naver.cafe.safeme.video.MyVideoActivity;

/**
 * Created by dasom on 2017-05-14.
 */
public class VideoActivityHandleCallback implements VideoActivityListener {
    private MyVideoActivity activity;

    public void setActivity(Activity activity) {
        this.activity = (MyVideoActivity) activity;
    }

    @Override
    public void onLocationChanged() {
        Context context = activity.getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("LAT_LNG_ADDR", 0);
        double lat = Double.parseDouble(sharedPreferences.getString("lattitude", ""));
        double lon = Double.parseDouble(sharedPreferences.getString("longitude", ""));
        String address = sharedPreferences.getString("address", "");
        activity.getUserInfo().setLat(lat);
        activity.getUserInfo().setLon(lon);
        activity.getUserInfo().setAddress(address);
        activity.sendInfo();
    }

    @Override
    public void onFinishedRecordVideo() {
        activity.getMyMuxing().stopVideoMuxing();
        activity.getMyMuxing().stopAudioMuxing();
        activity.getMyMuxing().stopMediaMuxer();
        activity.sendVideo();
    }

    @Override
    public void onStreamError() {
        activity.finish();
    }

    @Override
    public void onFinishedSendVideo() {
        activity.getUiHelper().onFinishedSendVideo();
        if (activity.getFlagManager().isPressedBack()) {
            activity.finish();
        }
    }

    @Override
    public void onFinishActivity() {
        if (activity.getFlagManager().isPressedBack()) {
            activity.finish();
        }
    }
}
