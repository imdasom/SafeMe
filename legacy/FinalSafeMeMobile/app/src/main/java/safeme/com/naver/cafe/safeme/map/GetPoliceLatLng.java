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
import safeme.com.naver.cafe.safeme.parser.JSONReciever;
import safeme.com.naver.cafe.safeme.parser.JSONSender;


// 주소로 이루어진 경찰서위치 DB를 지오코딩으로 위경도를 구해 다시 DB로 넘기는 클래스입니다.
public class GetPoliceLatLng extends FragmentActivity {

    private String[][] getPoliceLocation = null;
    private TextView log;

    private String TAG = "safeme";

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Constants.GET_POLICE_ADDRESS:
                    getPoliceLocation = (String[][])msg.obj;
                    log.append("받은 데이터 갯수 : "+getPoliceLocation.length+"\n\n");
                    Log.d("safeme", "lengthlenght : "+getPoliceLocation.length);
                    Geocoder coder = new Geocoder(getApplicationContext());

                    // 첫번째 경우
                    /*for (int i = 0; i < getPoliceLocation.length; i++) {
                        try {
                            List<Address> addresses = coder.getFromLocationName(getPoliceLocation[i][1], 1);

                            if (addresses != null && addresses.size() > 0) {
                                Address latlng = addresses.get(0);
                                log.append(getPoliceLocation[i][0]+", "+getPoliceLocation[i][1]+", "+latlng.getLatitude()+", "+latlng.getLongitude()+"\n");
                                Log.d("safeme", "length latlng : " + getPoliceLocation[i][0]+ ", "+ latlng.getLatitude() + ", " + latlng.getLongitude());
                                String sendData = "?id="+getPoliceLocation[i][0]+"&lat="+latlng.getLatitude()+"&lon="+latlng.getLongitude();
                                JSONSender policeposSender = new JSONSender();
                                policeposSender.setHandler(mHandler);
                                policeposSender.setUrl(Constants.PARSING_URL + Constants.PARSING_DIR_UPDATEPOLICEPOSLATLNG+sendData);
                                policeposSender.setParseType(Constants.SEND_POLICE_LATLNG);
                                policeposSender.start();
                            }
                        } catch (IOException e) {
                            Log.d("safeme", "onlocation gps addr : 주소 획득 실패");
                            e.printStackTrace();
                        }

                    }*/

                    // 두번째 경우
                    for (int i = 0; i < getPoliceLocation.length; i++) {
                        try {
                            String addr = getPoliceLocation[i][1];
                            String[] temp = addr.split(" ");
                            addr = "";
                            for(int j=0; j<temp.length-1; j++){
                                addr = addr + temp[j] + "%20";
                            }
                            addr = addr + temp[temp.length-1];
                            List<Address> addresses = coder.getFromLocationName(getPoliceLocation[i][1], 1);

                            if (addresses != null && addresses.size() > 0) {
                                Address latlng = addresses.get(0);
                                //log.append(getPoliceLocation[i][0]+", "+getPoliceLocation[i][1]+", "+latlng.getLatitude()+", "+latlng.getLongitude()+"\n");
                                Log.d("safeme", "length latlng : " + getPoliceLocation[i][0]+ ", "+ latlng.getLatitude() + ", " + latlng.getLongitude());
                                String sendData = "?id="+getPoliceLocation[i][0]+"&str="+addr+"&lat="+latlng.getLatitude()+"&lon="+latlng.getLongitude();
                                JSONSender policeposSender = new JSONSender();
                                policeposSender.setHandler(mHandler);
                                policeposSender.setUrl(Constants.PARSING_URL + Constants.PARSING_DIR_UPDATEPOLICEPOSLATLNG+sendData);
                                policeposSender.setParseType(Constants.SEND_POLICE_LATLNG);
                                policeposSender.start();
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
        log = (TextView)findViewById(R.id.log);

        // 첫번째 경우
        /*JSONReciever getpoliceLatLng = new JSONReciever();
        getpoliceLatLng.setHandler(mHandler);
        getpoliceLatLng.setUrl(Constants.PARSING_URL + Constants.PARSING_DIR_GETPOLICEPOSADDR);
        getpoliceLatLng.setParseType(Constants.GET_POLICE_ADDRESS);
        getpoliceLatLng.start();*/

        // 두번째 경우
        JSONReciever getpoliceLatLng = new JSONReciever();
        getpoliceLatLng.setHandler(mHandler);
        getpoliceLatLng.setUrl("http://192.168.0.6:8080/SafeMe/backpage/Policepos_getPos.jsp");
        getpoliceLatLng.setParseType(Constants.GET_POLICE_ADDRESS);
        getpoliceLatLng.start();

    }
}
