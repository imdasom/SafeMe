package safeme.com.naver.cafe.safeme.http.callback;

import android.os.Handler;

import org.apache.http.client.methods.HttpPost;

import java.io.File;

import safeme.com.naver.cafe.safeme.utils.IOUtils;
import safeme.com.naver.cafe.safeme.constants.Constants;
import safeme.com.naver.cafe.safeme.http.HttpUtils;
import safeme.com.naver.cafe.safeme.http.ProcessCallback;

/**
 * Created by dasom on 2017-04-30.
 */
public class SendVideoFileCallback implements ProcessCallback {
    private static final int WHAT = Constants.ON_FINISHED_SEND_VIDEO;
    private Handler handler;
    private String fileName;

    public SendVideoFileCallback(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void onPreExecute(Object... objects) {
        HttpPost post = (HttpPost) objects[0];
        File videoFile = IOUtils.getVideoFile(fileName);
        post.setEntity(HttpUtils.getMultipartEntity("video", videoFile));
    }

    @Override
    public void onPostExecute(Object... objects) {
        handler.sendMessage(HttpUtils.getMessage(WHAT, null));
    }
}
