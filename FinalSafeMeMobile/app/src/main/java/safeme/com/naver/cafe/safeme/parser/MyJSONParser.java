package safeme.com.naver.cafe.safeme.parser;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by safeme on 2015-12-02.
 */
public abstract class MyJSONParser extends Thread{

    protected static String TAG = "Safeme/MyJSONParser";
    protected Handler mHandler;

    protected String mURL;
    protected String mFilename;
    protected int mParseType;
    protected String mPath;

    protected HttpPost post;
    protected HttpClient client;

    protected Message msg;

    public MyJSONParser(){
        mHandler = null;
        mURL = null;
        mFilename = null;
        mParseType = 0;
        mPath = Environment.getExternalStorageDirectory().getPath();

        post = null;
        client = null;
        msg = null;
    }

    public void setHandler(Handler mHandler){
        this.mHandler = mHandler;
    }

    public Handler getHandler(){
        return mHandler;
    }

    public void setUrl(String mURL){
        this.mURL = mURL;
    }

    public String getUrl(){
        return mURL;
    }

    public void setFilename(String mFilename){
        this.mFilename = mFilename;
    }

    public String getFilename(){
        return mFilename;
    }

    public void setParseType(int mParseType){
        this.mParseType = mParseType;
    }

    public int getParseType(){
        return mParseType;
    }

    @Override
    public void run(){
        super.run();
        post = new HttpPost(mURL);
        client = new DefaultHttpClient();
        startParsing();
    }

    protected abstract void startParsing();

}
