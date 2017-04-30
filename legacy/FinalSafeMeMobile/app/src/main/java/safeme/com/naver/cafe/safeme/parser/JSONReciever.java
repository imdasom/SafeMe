package safeme.com.naver.cafe.safeme.parser;

import android.os.Message;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import safeme.com.naver.cafe.safeme.constants.Constants;

/**
 * Created by safeme on 2015-12-02.
 */
public class JSONReciever extends MyJSONParser {

    @Override
    protected void startParsing() {
        try {
            String[][] getJsonData = null;
            HttpResponse response = client.execute(post);
            BufferedReader bufreader = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent(), "utf-8"));

            String line = "";
            StringBuilder sb = new StringBuilder();

            while ((line = bufreader.readLine()) != null) {
                sb.append(line.trim());
            }
            String dataRecieve = sb.toString();
            Log.d(TAG, mParseType+" recieve data policepos : " + dataRecieve);

            JSONObject json = null;
            JSONArray jArr = null;

            switch (mParseType){
                // 경찰서의 아이디-주소 데이터를 받아오는 케이스
                case Constants.GET_POLICE_ADDRESS:
                    json = new JSONObject(dataRecieve);
                    jArr = json.getJSONArray("policepos");

                    getJsonData = new String[jArr.length()][2];
                    for (int i = 0; i < jArr.length(); i++) {
                        json = jArr.getJSONObject(i);
                        getJsonData[i][0] = json.getString("id");
                        getJsonData[i][1] = json.getString("loc_str");
                    }
                    break;

                case Constants.GET_POLICEPOS_DATA:
                    json = new JSONObject(dataRecieve);
                    jArr = json.getJSONArray("policepos");

                    getJsonData = new String[jArr.length()][4];
                    for (int i = 0; i < jArr.length(); i++) {
                        json = jArr.getJSONObject(i);
                        getJsonData[i][0] = json.getString("id");
                        getJsonData[i][1] = json.getString("loc_lat");
                        getJsonData[i][2] = json.getString("loc_lon");
                        getJsonData[i][3] = json.getString("loc_str");
                    }
                    break;

                case Constants.GET_FOOTPOS_DATA:
                    json = new JSONObject(dataRecieve);
                    jArr = json.getJSONArray("footpos");

                    getJsonData = new String[jArr.length()][3];
                    for (int i = 0; i < jArr.length(); i++) {
                        json = jArr.getJSONObject(i);
                        getJsonData[i][0] = json.getString("id");
                        getJsonData[i][1] = json.getString("lat");
                        getJsonData[i][2] = json.getString("lon");
                    }
                    break;
            }
            msg = new Message();
            msg.what = mParseType;
            msg.obj = getJsonData;
            mHandler.sendMessage(msg);
        }catch (Exception e) {
            e.printStackTrace();
            msg = new Message();
            msg.what = Constants.PARSING_ERROR;
            mHandler.sendMessage(msg);
        }
    }
}
