package safeme.com.naver.cafe.safeme.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import safeme.com.naver.cafe.safeme.obj.PoliceAddress;

/**
 * Created by dasom on 2017-05-09.
 */
public class Parser {

    // gson이용해서 클래스 파일 넣어서 읽어오도록 하는게 깔끔할 듯
    public static void jsonArrayToJavaList(String source, String name, String[][] result) {
        try {

            JSONObject jsonObject = new JSONObject(source);
            JSONArray jsonArray = jsonObject.getJSONArray(name);
            result = new String[jsonArray.length()][2];
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                result[i][0] = jsonObject.getString("id");
                result[i][1] = jsonObject.getString("loc_str");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static <T> void jsonArrayToJavaList(String dataReceived, List<T> dataParsed) {
        Gson gson = new GsonBuilder().create();
        dataParsed = gson.fromJson(dataReceived, new TypeToken<List<T>>(){}.getType());
    }
}
