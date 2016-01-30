package safeme.com.naver.cafe.safeme.video.streaming;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaMuxer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;

import net.majorkernelpanic.streaming.Session;
import net.majorkernelpanic.streaming.SessionBuilder;
import net.majorkernelpanic.streaming.audio.AudioQuality;
import net.majorkernelpanic.streaming.gl.SurfaceView;
import net.majorkernelpanic.streaming.rtp.MyMuxing;
import net.majorkernelpanic.streaming.rtsp.RtspClient;
import net.majorkernelpanic.streaming.video.VideoQuality;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import safeme.com.naver.cafe.safeme.constants.Constants;

/**
 *  실시간 스트리밍을 관장하는 클래스
 */
public class MyStreaming implements RtspClient.Callback,
        Session.Callback, SurfaceHolder.Callback{
    private static String TAG = "SafeMe/MyStreaming";

    // Application Context
    private Context mContext;
    private Handler mHandler;

    // surfaceview
    private static SurfaceView mSurfaceView;

    // Rtsp session
    private Session mSession;
    private static RtspClient mClient;

    private MyMuxing myMuxing;
    private String serverip;

    public MyStreaming(){
        mSession = null;
        mClient = null;
    }

    public void setContext(Context mContext){
        this.mContext = mContext;
    }

    public void setHandler(Handler mHandler) { this.mHandler = mHandler; }

    public void setServerip(String serverip){
        this.serverip = serverip;
    }

    public void setSurfaceView(SurfaceView mSurfaceView){
        this.mSurfaceView = mSurfaceView;
    }

    public void init() throws IOException {
        if(mSurfaceView == null)
            Log.d(TAG, "Call after Setting SurfaceView!");
        mSurfaceView.getHolder().addCallback(this);

        initRtspClient();
    }

    public void setMediaMuxer(MyMuxing myMuxing) throws IOException {
        //this.file = file;
        this.myMuxing = myMuxing;
        Log.d("safeme", "MyMuxing myStreaming에 세팅됨 : "+myMuxing);
    }

    private void initRtspClient() throws IOException {
        // Configures the SessionBuilder
        mSession = SessionBuilder.getInstance()
                .setContext(mContext)
                .setAudioEncoder(SessionBuilder.AUDIO_AAC)
                .setAudioQuality(new AudioQuality(8000, 16000))
                .setVideoEncoder(SessionBuilder.VIDEO_H264)
                .setVideoQuality(new VideoQuality(176, 144, 20, 500000))
                .setSurfaceView(mSurfaceView).setPreviewOrientation(0)
                .setCallback(this).build();

        // Configures the RTSP client
        mClient = new RtspClient();
        mClient.setSession(mSession);
        mClient.setCallback(this);
        mSurfaceView.setAspectRatioMode(SurfaceView.ASPECT_RATIO_STRETCH);
        String ip, port, path;

        // We parse the URI written in the Editext
        Pattern uri = Pattern.compile("rtsp://(.+):(\\d+)/(.+)");
        Matcher m = uri.matcher(Constants.STREAM_URL);

        m.find();
        ip = m.group(1);
        port = m.group(2);
        path = m.group(3);
        Log.d("pattern", "1, 2, 3 : " + ip + ", " + port + ", " + path);

        mClient.setCredentials(Constants.PUBLISHER_USERNAME,
                Constants.PUBLISHER_PASSWORD);
        mClient.setServerAddress(ip, Integer.parseInt(port));
        mClient.setStreamPath("/" + path);

        mSession.getVideoTrack().setMediaMuxer(myMuxing);        //safeme
        mSession.getAudioTrack().setMediaMuxer(myMuxing);        //safeme
        mSession.getVideoTrack().setHandler(mHandler);           //safeme
    }

    public void toggleStreaming() {
        if (!mClient.isStreaming()) {
            // Start camera preview
            mSession.startPreview();

            // Start video stream
            mClient.startStream();
        } else {
            // already streaming, stop streaming
            // stop camera preview
            mSession.stopPreview();

            // stop streaming
            mClient.stopStream();
        }
    }

    public void destroyStreaming(){
        mClient.release();
        mSession.release();
        mSurfaceView.getHolder().removeCallback(this);
    }

    @Override
    public void onSessionError(int reason, int streamType, Exception e) {
        Log.d(TAG, "onSessionError");
        switch (reason) {
            case Session.ERROR_CAMERA_ALREADY_IN_USE:
                break;
            case Session.ERROR_CAMERA_HAS_NO_FLASH:
                break;
            case Session.ERROR_INVALID_SURFACE:
                break;
            case Session.ERROR_STORAGE_NOT_READY:
                break;
            case Session.ERROR_CONFIGURATION_NOT_SUPPORTED:
                break;
            case Session.ERROR_OTHER:
                break;
        }

        if (e != null) {
            alertError(e.getMessage());
            e.printStackTrace();
        }
    }

    private void alertError(final String msg) {
        /*final String error = (msg == null) ? "Unknown error: " : msg;
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(error).setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();*/

        Log.d(TAG, msg);
        Message message = new Message();
        message.what = Constants.STREAM_ERROR;
        mHandler.sendMessage(message);
    }

    @Override
    public void onRtspUpdate(int message, Exception exception) {
        switch (message) {
            case RtspClient.ERROR_CONNECTION_FAILED:
            case RtspClient.ERROR_WRONG_CREDENTIALS:
                alertError(exception.getMessage());
                exception.printStackTrace();
                break;
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onBitrateUpdate(long bitrate) {

    }

    @Override
    public void onPreviewStarted() {

    }

    @Override
    public void onSessionConfigured() {

    }

    @Override
    public void onSessionStarted() {

    }

    @Override
    public void onSessionStopped() {

    }
}
