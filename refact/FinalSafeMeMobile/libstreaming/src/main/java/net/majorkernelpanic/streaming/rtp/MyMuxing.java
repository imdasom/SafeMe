package net.majorkernelpanic.streaming.rtp;

import android.annotation.TargetApi;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.Build;
import android.util.Log;

import java.io.IOException;

/**
 * Created by safeme on 2015-12-07.
 */
public class MyMuxing extends Thread{
    protected MediaMuxer mMediaMuxer;
    protected int audioTrackIndex;
    protected int videoTrackIndex;
    protected MediaFormat audioFormat;
    protected MediaFormat videoFormat;
    protected boolean videoState;
    protected boolean audioState;
    protected boolean state;
    public long timeStamp = 0;

    private byte[] sps = null, pps = null;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public MyMuxing(String filename) throws IOException {
        mMediaMuxer = new MediaMuxer(filename+".mp4", MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
        audioTrackIndex = 0;
        videoTrackIndex = 0;
        videoState = false;
        audioState = false;
        state = false;
    }

    public boolean isStartMuxing(){
        return (audioState&&videoState);
    }

    public MediaMuxer getMediaMuxer(){
        return mMediaMuxer;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void startMediaMuxer(){
        if(audioFormat!=null && videoFormat!=null) {
            if(!state) {
                videoTrackIndex = mMediaMuxer.addTrack(videoFormat);
                audioTrackIndex = mMediaMuxer.addTrack(audioFormat);
                mMediaMuxer.start();
                Log.d("safeme", "start muxing");
                videoState = true;
                audioState = true;
                state = true;
            }
        }else{
            state = false;
        }
    }

    public void stopAudioMuxing(){
        audioState = false;
    }

    public void stopVideoMuxing(){
        videoState = false;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public boolean stopMediaMuxer(){
        try{
            if(mMediaMuxer!=null){
                mMediaMuxer.stop();
                mMediaMuxer.release();
                mMediaMuxer = null;
                videoState = false;
                audioState = false;
                state = false;
                videoTrackIndex = 0;
                audioTrackIndex = 0;
                videoFormat = null;
                audioFormat = null;
                Log.d("safeme", "stop muxing");
            }
        }catch (RuntimeException e){
            return false;
        }
        return true;

    }

    public int getVideoTrackIndex(){
        return videoTrackIndex;
    }

    public void setAudioFormet(MediaFormat mediaFormat){
        this.audioFormat = mediaFormat;
    }

    public MediaFormat getAudioFormet(){
        return audioFormat;
    }

    public void setVideoFormet(MediaFormat mediaFormat){
        this.videoFormat = mediaFormat;
    }

    public MediaFormat getVideoFormat(){
        return videoFormat;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void addVideoTrack(MediaFormat mediaFormat)
    {
        videoFormat = mediaFormat;
        Log.d("safme", "MyMuxing  addVideoTrack() : "+mediaFormat);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void addAudioTrack(MediaFormat mediaFormat)
    {
        audioFormat = mediaFormat;
        Log.d("safme", "MyMuxing  addAudioTrack() : "+mediaFormat);
    }

    public int getAudioTrackIndex(){
        return audioTrackIndex;
    }

    public void setStreamParameters(byte[] pps, byte[] sps) {
        this.pps = pps;
        this.sps = sps;
    }

    public byte[] getPPS(){
        return pps;
    }

    public byte[] getSPS(){
        return sps;
    }
}
