package safeme.com.naver.cafe.safeme.http.service;

import android.os.Handler;

import safeme.com.naver.cafe.safeme.constants.Constants;
import safeme.com.naver.cafe.safeme.http.HttpUtils;
import safeme.com.naver.cafe.safeme.http.IServiceController;

/**
 * Created by dasom on 2017-04-30.
 */
public class NotifyInfoSendService implements IServiceController {
    private static final int WHAT = Constants.SEND_NOFITYINFO;
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
        handler.sendMessage(HttpUtils.getMessage(WHAT));
    }
}
