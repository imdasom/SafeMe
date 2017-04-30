package safeme.com.naver.cafe.safeme.parser;

import android.os.Message;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;

import java.io.File;
import java.io.IOException;

import safeme.com.naver.cafe.safeme.constants.Constants;

/**
 * Created by safeme on 2015-12-02.
 */
public class JSONSender extends MyJSONParser {
    @Override
    protected void startParsing() {
        try {
            switch (mParseType){
                case Constants.SEND_NOFITYINFO:
                    client.execute(post); //데이터를 제대로 받아왔는지 확인을 위해 서버에서 true/false를 리턴하면 더 좋을듯!
                    break;

                case Constants.SEND_VIDEOFILE:
                    //여기서 오류테스트하기 FILENAME이 null이 아닌지 등등의 테스트
                    File glee = new File(mPath+"/safeme/"+mFilename+".mp4");
                    FileBody bin = new FileBody(glee);
                    MultipartEntity multipart = new MultipartEntity(
                            HttpMultipartMode.BROWSER_COMPATIBLE);
                    multipart.addPart("video", bin);
                    post.setEntity(multipart);

                    try {
                        client.execute(post);
                        msg = new Message();
                        msg.what = Constants.PARSING_ONPROGRESS;
                    } catch (IOException e) {
                        e.printStackTrace();
                        msg = new Message();
                        msg.what = Constants.PARSING_ERROR;
                        msg.obj = e;
                        mHandler.sendMessage(msg);
                    }finally {
                    }

                    break;
            }
            msg = new Message();
            msg.what = mParseType;
            mHandler.sendMessage(msg);

        }catch (Exception e) {
            e.printStackTrace();
            msg = new Message();
            msg.what = Constants.PARSING_ERROR;
            mHandler.sendMessage(msg);
        }
    }
}
