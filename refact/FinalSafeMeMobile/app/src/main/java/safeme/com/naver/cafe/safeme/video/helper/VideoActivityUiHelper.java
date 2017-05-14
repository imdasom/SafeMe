package safeme.com.naver.cafe.safeme.video.helper;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import safeme.com.naver.cafe.safeme.R;
import safeme.com.naver.cafe.safeme.constants.Constants;
import safeme.com.naver.cafe.safeme.video.util.VideoActivityFlag;

/**
 * Created by dasom on 2017-05-09.
 */
public class VideoActivityUiHelper {
    private Context context;
    private TextView logRecord;
    private Button stopAndSend;
    private Button toMain;
    private VideoActivityFlag flagManager;
    private Handler handler;

    public VideoActivityUiHelper(Context context, VideoActivityFlag flagManager, Handler handler) {
        this.context = context;
        this.flagManager = flagManager;
        this.handler = handler;

        Activity activity = (Activity) context;
        initTextView(activity);
        initStopAndSendButton(activity);
        initToMainButton(activity);
    }

    private void initStopAndSendButton(Activity activity) {
        stopAndSend = (Button) activity.findViewById(R.id.stopAndSend);
        stopAndSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!flagManager.isStartedSending()) {
                    Message msg = new Message();
                    msg.what = Constants.ON_FINISHED_RECORD_VIDEO;
                    handler.sendMessage(msg);
                }
            }
        });
    }

    private void initToMainButton(Activity activity) {
        toMain = (Button) activity.findViewById(R.id.toMain);
        toMain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                flagManager.setIsPressedBack(true);

                if (!flagManager.isStartedSending()) { // 전송을 마치고 액티비티를 종료해야 한다.
                    Message msg = new Message();
                    msg.what = Constants.ON_FINISHED_RECORD_VIDEO;
                    handler.sendMessage(msg);
                } else {
                    Message msg = new Message();
                    msg.what = Constants.FINISH_VIDEO_ACTIVITY;
                    handler.sendMessage(msg);
                }
            }
        });
    }

    private void initTextView(Activity activity) {
        logRecord = (TextView) activity.findViewById(R.id.log);
        logRecord.setMovementMethod(new ScrollingMovementMethod());
        logRecord.append("onCreate()\n");
    }


    public void onInitStreaming() {
        logRecord.append("Initialize Streaming\n");
    }

    public void onStartSendVideo() {
        Toast.makeText(context, "파일을 전송하는 중입니다...", Toast.LENGTH_SHORT).show();
    }

    public void onFinishedSendVideo() {
        stopAndSend.setBackgroundResource(R.drawable.button_green);
    }

    public void onStartSendUserInfo() {
        logRecord.append("Initialize Sending\n");
    }

    public void onFinishedSendUserInfo() {
        Log.d("safeme", "notify : 정보가 접수됨");
    }

    public void onResume() {
        logRecord.append("onResume() called\n");
    }

    public void onPause() {
        logRecord.append("onPause() called\n");
    }

    public void onDestroy() {
        logRecord.append("onDestroy() called\n");
    }

}
