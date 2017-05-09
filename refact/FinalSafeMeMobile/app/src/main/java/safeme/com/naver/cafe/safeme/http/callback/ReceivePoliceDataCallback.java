package safeme.com.naver.cafe.safeme.http.callback;

import android.os.Handler;

import org.apache.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import safeme.com.naver.cafe.safeme.constants.Constants;
import safeme.com.naver.cafe.safeme.http.HttpUtils;
import safeme.com.naver.cafe.safeme.http.ProcessCallback;
import safeme.com.naver.cafe.safeme.obj.PoliceAddress;
import safeme.com.naver.cafe.safeme.obj.PoliceData;
import safeme.com.naver.cafe.safeme.utils.IOUtils;
import safeme.com.naver.cafe.safeme.utils.Parser;

/**
 * Created by dasom on 2017-05-09.
 */
public class ReceivePoliceDataCallback implements ProcessCallback {
    private static final int WHAT = Constants.GET_POLICEPOS_DATA;
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
        HttpResponse response = (HttpResponse) objects[0];

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
            String dataReceived = IOUtils.readData(br);
            List<PoliceData> dataParsed = null;
            Parser.jsonArrayToJavaList(dataReceived, dataParsed);
            handler.sendMessage(HttpUtils.getMessage(ReceivePoliceDataCallback.WHAT, dataParsed));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
