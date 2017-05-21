package safeme.com.naver.cafe.safeme.video;import android.content.Intent;import android.os.Bundle;import android.support.v7.app.AppCompatActivity;import android.view.Window;import android.view.WindowManager;import net.majorkernelpanic.streaming.gl.SurfaceView;import net.majorkernelpanic.streaming.rtp.MyMuxing;import java.io.File;import java.io.IOException;import java.text.SimpleDateFormat;import java.util.Date;import safeme.com.naver.cafe.safeme.R;import safeme.com.naver.cafe.safeme.constants.Constants;import safeme.com.naver.cafe.safeme.http.HttpConnector;import safeme.com.naver.cafe.safeme.http.HttpUtils;import safeme.com.naver.cafe.safeme.http.ProcessCallback;import safeme.com.naver.cafe.safeme.http.callback.SendUserInfoCallback;import safeme.com.naver.cafe.safeme.http.callback.SendVideoFileCallback;import safeme.com.naver.cafe.safeme.map.GpsManager;import safeme.com.naver.cafe.safeme.utils.IOUtils;import safeme.com.naver.cafe.safeme.video.handle.VideoActivityHandleCallback;import safeme.com.naver.cafe.safeme.video.handle.VideoActivityHandler;import safeme.com.naver.cafe.safeme.video.helper.VideoActivityUiHelper;import safeme.com.naver.cafe.safeme.video.streaming.MyStreaming;import safeme.com.naver.cafe.safeme.video.util.UserInfo;import safeme.com.naver.cafe.safeme.video.util.VideoActivityFlag;/** * '신고하기' 기능을 총괄하는 클래스 */public class MyVideoActivity extends AppCompatActivity {    public final static String TAG = "SsfeMe::MyVideoActivity";    // 메인 객체 선언    private MyStreaming myStreaming;    private MyMuxing myMuxing;    private GpsManager gpsManager;    // refactoring....    private File videoFile;    private UserInfo userInfo;    private VideoActivityHandler handler;    private VideoActivityHandleCallback handleCallback;    private VideoActivityUiHelper uiHelper;    private VideoActivityFlag flagManager;    @Override    protected void onCreate(Bundle savedInstanceState) {        initEnvironments();        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_video);        initMemberField();        initStreaming();        sendInfo();         // 사용자 정보는 먼저 전송한다. 비디오는 나중에 전송.    }    private void initEnvironments() {        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);        requestWindowFeature(Window.FEATURE_NO_TITLE);    }    private void initMemberField() {        userInfo = initUserInfo();        videoFile = IOUtils.getVideoFile(userInfo.getFileName()); // Initialize File        flagManager = new VideoActivityFlag();        handleCallback = new VideoActivityHandleCallback();        handleCallback.setActivity(MyVideoActivity.this);        handler = new VideoActivityHandler(handleCallback);        uiHelper = new VideoActivityUiHelper(getApplicationContext(), flagManager, handler); // 뷰 초기화    }    private UserInfo initUserInfo() {        Intent intent = getIntent();        double lat = intent.getDoubleExtra("lattitude", 0.0f);        double lng = intent.getDoubleExtra("longitude", 0.0f);        String addr = intent.getStringExtra("address");        String phonenum1 = intent.getStringExtra("phonenum1");        String phonenum2 = intent.getStringExtra("phonenum2");        String videoFileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());        return new UserInfo(new String[]{phonenum1, phonenum2}, videoFileName, addr, lat, lng);    }    private void initStreaming() {        try {            uiHelper.onInitStreaming();            myMuxing = new MyMuxing(videoFile.getPath() + File.separator + userInfo.getFileName());            myStreaming = new MyStreaming();            myStreaming.setContext(getApplicationContext());            myStreaming.setHandler(handler);            myStreaming.setSurfaceView((SurfaceView) findViewById(R.id.surface)); //surfaceView를 세팅합니다.            myStreaming.setMediaMuxer(myMuxing);            myStreaming.init(); //초기화해줍니다.        } catch (IOException e) {            e.printStackTrace();        }    }    public void sendVideo() {        uiHelper.onStartSendVideo();        ProcessCallback callback = new SendVideoFileCallback(userInfo.getFileName() + ".mp4");        String url = Constants.PARSING_URL + Constants.PARSING_DIR_VIDEO;        HttpConnector httpConnector = HttpUtils.getHttpConnector(url, callback, handler);        while (!flagManager.isStartedSending()) {            if (!myMuxing.isStartMuxing()) {                httpConnector.start();                flagManager.setStartedSending(true);                break;            }        }    }    public void sendInfo() {        if (flagManager.isUserInfoSended()) {            return;        }        if (!checkValidations()) {            return;        }        uiHelper.onStartSendUserInfo();        ProcessCallback callback = new SendUserInfoCallback();        String url = Constants.PARSING_URL + Constants.PARSING_DIR_NOFIFYINFO + userInfo.getPostData();        HttpConnector httpConnector = HttpUtils.getHttpConnector(url, callback, handler);        httpConnector.start();        flagManager.setIsUserInfoSended(true);        uiHelper.onFinishedSendUserInfo();    }    private boolean checkValidations() {        if (!checkLocationValueValidation()) {            if (!checkGpsManagerValidation(gpsManager)) {                userInfo.setLat(37);                userInfo.setLon(126);                return true;            }        }        return false;    }    private boolean checkLocationValueValidation() {        if (userInfo.getLat() > 0 && userInfo.getLon() > 0) {            return true;        } else {            return false;        }    }    private boolean checkGpsManagerValidation(GpsManager gpsManager) {        if (gpsManager == null) {            gpsManager = new GpsManager();            gpsManager.setContext(getApplicationContext());            gpsManager.setMyVideoHandler(handler);            if (!gpsManager.initGps()) {                return false;            }        }        return true;    }    @Override    protected void onResume() {        uiHelper.onResume();        super.onResume();        myStreaming.toggleStreaming(); // toggle streaming        flagManager.setIsPressedBack(false);    }    @Override    public void onPause() {        uiHelper.onPause();        super.onPause();        myStreaming.toggleStreaming(); // toggle streaming    }    @Override    public void onBackPressed() {        //backpress 대신 다른 버튼을 눌러 액티비티를 종료하도록 함    }    @Override    public void onDestroy() {        uiHelper.onDestroy();        myStreaming.destroyStreaming(); // stop streaming        super.onDestroy();    }    public MyMuxing getMyMuxing() {        return myMuxing;    }    public VideoActivityUiHelper getUiHelper() {        return uiHelper;    }    public VideoActivityFlag getFlagManager() {        return flagManager;    }    public UserInfo getUserInfo() {        return userInfo;    }}