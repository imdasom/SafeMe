package safeme.com.naver.cafe.safeme.http;

import android.os.Handler;
import android.os.Message;

import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

/**
 * Created by dasom on 2017-04-30.
 */
public class HttpUtils {
    public static HttpConnector getHttpConnector(String url, ProcessCallback serviceController, Handler handler) {
        HttpConnector httpConnector = new HttpConnector();
        httpConnector.setUrl(url);
        httpConnector.setIServiceController(serviceController);
        httpConnector.setHandler(handler);
        return httpConnector;
    }


    public static HttpEntity getMultipartEntity(String name, File file) {
        // 이 부분 팩토리로 바꿀 수 있지 않을까...
        FileBody fileBody = new FileBody(file);
        MultipartEntity multipart = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        multipart.addPart(name, fileBody);
        return multipart;
    }

    public static Message getMessage(int what, Object obj) {
        Message message = new Message();
        message.what = what;
        message.obj = obj;
        return message;
    }

}
