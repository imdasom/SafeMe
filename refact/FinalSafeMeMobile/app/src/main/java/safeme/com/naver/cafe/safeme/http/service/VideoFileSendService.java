package safeme.com.naver.cafe.safeme.http.service;

import android.os.Handler;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;

import safeme.com.naver.cafe.safeme.constants.Constants;
import safeme.com.naver.cafe.safeme.http.HttpUtils;
import safeme.com.naver.cafe.safeme.http.IServiceController;

/**
 * Created by dasom on 2017-04-30.
 */
public class VideoFileSendService implements IServiceController {
    private static final int WHAT = Constants.PARSING_ONPROGRESS;
    private Handler handler;
    private String fileName;

    public VideoFileSendService(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void onPreExecute(Object... objects) {
        HttpPost post = (HttpPost) objects[0];
        post.setEntity(getMultipartEntity());
    }

    @Override
    public void onPostExecute(Object... objects) {
        handler.sendMessage(HttpUtils.getMessage(WHAT));
    }

    private HttpEntity getMultipartEntity() {
        // 이 부분 팩토리로 바꿀 수 있지 않을까...
        FileBody fileBody = new FileBody(HttpUtils.getVideoFile(fileName));
        MultipartEntity multipart = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        multipart.addPart("video", fileBody);
        return multipart;
    }
}
