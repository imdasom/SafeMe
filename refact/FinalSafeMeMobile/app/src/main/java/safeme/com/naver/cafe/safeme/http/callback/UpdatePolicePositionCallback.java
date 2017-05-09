package safeme.com.naver.cafe.safeme.http.callback;

import android.os.Handler;

import safeme.com.naver.cafe.safeme.constants.Constants;
import safeme.com.naver.cafe.safeme.http.HttpUtils;
import safeme.com.naver.cafe.safeme.http.ProcessCallback;

/**
 * Created by dasom on 2017-05-09.
 */
public class UpdatePolicePositionCallback implements ProcessCallback {
    private static final int WHAT = Constants.SEND_POLICE_LATLNG;
    private Handler handler;

    @Override
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void onPreExecute(Object... objects) {

    }

    @Override
    public void onPostExecute(Object... objects) {
        handler.sendMessage(HttpUtils.getMessage(UpdatePolicePositionCallback.WHAT, null));
    }
}
