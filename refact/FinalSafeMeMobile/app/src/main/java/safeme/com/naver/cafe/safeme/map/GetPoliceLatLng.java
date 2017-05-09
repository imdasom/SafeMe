package safeme.com.naver.cafe.safeme.map;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import safeme.com.naver.cafe.safeme.R;
import safeme.com.naver.cafe.safeme.constants.Constants;
import safeme.com.naver.cafe.safeme.http.HttpConnector;
import safeme.com.naver.cafe.safeme.http.HttpUtils;
import safeme.com.naver.cafe.safeme.http.ProcessCallback;
import safeme.com.naver.cafe.safeme.http.callback.ReceivePoliceAdressCallback;
import safeme.com.naver.cafe.safeme.http.callback.UpdatePolicePositionCallback;
import safeme.com.naver.cafe.safeme.obj.PoliceAddress;


// 주소로 이루어진 경찰서위치 DB를 지오코딩으로 위경도를 구해 다시 DB로 넘기는 클래스입니다.
public class GetPoliceLatLng extends FragmentActivity {

    private List<PoliceAddress> policeAddressList = null;
    private TextView log;

    private String TAG = "safeme";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constants.GET_POLICE_ADDRESS:
                    policeAddressList = (List<PoliceAddress>) msg.obj;
                    log.append("받은 데이터 갯수 : " + policeAddressList.size() + "\n\n");
                    Log.d("safeme", "lengthlenght : " + policeAddressList.size());
                    Geocoder coder = new Geocoder(getApplicationContext());

                    // 첫번째 경우
//                    for (int i = 0; i < policeAddressList.size(); i++) {
//                        try {
//                            List<Address> addresses = coder.getFromLocationName(policeAddressList.get(i).getLoc_str(), 1);
//
//                            if (addresses != null && addresses.size() > 0) {
//                                Address latlng = addresses.get(0);
//                                log.append(policeAddressList.get(i).toString() + ", " + latlng.getLatitude() + ", " + latlng.getLongitude() + "\n");
//                                Log.d("safeme", "length latlng : " + policeAddressList.get(i).getId() + ", " + latlng.getLatitude() + ", " + latlng.getLongitude());
//                                String sendData = "?id=" + policeAddressList.get(i).getId() + "&lat=" + latlng.getLatitude() + "&lon=" + latlng.getLongitude();
//
//                                String url = Constants.PARSING_URL + Constants.PARSING_DIR_UPDATEPOLICEPOSLATLNG + sendData;
//                                ProcessCallback callback = new UpdatePolicePositionCallback();
//
//                                HttpConnector httpConnector = HttpUtils.getHttpConnector(url, callback, handler);
//                                httpConnector.start();
//                            }
//                        } catch (IOException e) {
//                            Log.d("safeme", "onlocation gps addr : 주소 획득 실패");
//                            e.printStackTrace();
//                        }
//
//                    }

                    // 두번째 경우
                    for (int i = 0; i < policeAddressList.size(); i++) {
                        try {
                            String addr = policeAddressList.get(i).getLoc_str();
                            String[] temp = addr.split(" ");
                            addr = "";
                            for (int j = 0; j < temp.length - 1; j++) {
                                addr = addr + temp[j] + "%20";
                            }
                            addr = addr + temp[temp.length - 1];
                            List<Address> addresses = coder.getFromLocationName(policeAddressList.get(i).getLoc_str(), 1);

                            if (addresses != null && addresses.size() > 0) {
                                Address latlng = addresses.get(0);
//                                log.append(policeAddressList[i][0]+", "+policeAddressList[i][1]+", "+latlng.getLatitude()+", "+latlng.getLongitude()+"\n");
                                Log.d("safeme", "length latlng : " + policeAddressList.get(i).getId() + ", " + latlng.getLatitude() + ", " + latlng.getLongitude());
                                String sendData = "?id=" + policeAddressList.get(i).getId() + "&str=" + addr + "&lat=" + latlng.getLatitude() + "&lon=" + latlng.getLongitude();

                                String url = Constants.PARSING_URL + Constants.PARSING_DIR_UPDATEPOLICEPOSLATLNG + sendData;
                                ProcessCallback callback = new UpdatePolicePositionCallback();

                                HttpConnector httpConnector = HttpUtils.getHttpConnector(url, callback, handler);
                                httpConnector.start();
                            }
                        } catch (IOException e) {
                            Log.d("safeme", "onlocation gps addr : 주소 획득 실패");
                            e.printStackTrace();
                        }

                    }

                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getlatlng);
        log = (TextView) findViewById(R.id.log);

        // 첫번째 경우
//        ProcessCallback serviceController = new ReceivePoliceAdressCallback();
//        String url = "Constants.PARSING_URL + Constants.PARSING_DIR_GETPOLICEPOSADDR";
//
//        HttpConnector httpConnector = HttpUtils.getHttpConnector(url, serviceController, handler);
//        httpConnector.start();

        // 두번째 경우
        ProcessCallback serviceController = new ReceivePoliceAdressCallback();
        String url = "http://192.168.0.6:8080/SafeMe/backpage/Policepos_getPos.jsp";

        HttpConnector httpConnector = HttpUtils.getHttpConnector(url, serviceController, handler);
        httpConnector.start();

    }
}
