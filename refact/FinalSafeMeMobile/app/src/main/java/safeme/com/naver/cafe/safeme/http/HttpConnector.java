package safeme.com.naver.cafe.safeme.http;

import android.os.Handler;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by dasom on 2017-04-30.
 */
public class HttpConnector extends Thread {
    private String url;
    private Handler handler;
    private ProcessCallback serviceController;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void setIServiceController(ProcessCallback processCallback) {
        this.serviceController = processCallback;
    }

    @Override
    public void run() {
        HttpPost post = new HttpPost(url);
        HttpClient client = new DefaultHttpClient();

        try {
            serviceController.setHandler(handler);
            serviceController.onPreExecute(post);
            HttpResponse response = client.execute(post);
            serviceController.onPostExecute(response);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
