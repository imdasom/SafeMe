package safeme.com.naver.cafe.safeme.http;

import android.os.Environment;
import android.os.Message;

import java.io.File;

/**
 * Created by dasom on 2017-04-30.
 */
public class HttpUtils {

    public static File getVideoFile(String fileName) {
        String path = Environment.getExternalStorageDirectory().getPath();

        //여기서 오류테스트하기 FILENAME이 null이 아닌지 등등의 테스트
        File file = new File(path+"/safeme/"+fileName+".mp4");
        return file;
    }

    public static Message getMessage(int what) {
        Message message = new Message();
        message.what = what;
        return message;
    }
}
