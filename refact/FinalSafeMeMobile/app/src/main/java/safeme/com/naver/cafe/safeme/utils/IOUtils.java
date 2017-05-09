package safeme.com.naver.cafe.safeme.utils;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

/**
 * Created by dasom on 2017-05-09.
 */
public class IOUtils {
    public static File getVideoFile(String fileName) {
        String path = Environment.getExternalStorageDirectory().getPath();

        //여기서 오류테스트하기 FILENAME이 null이 아닌지 등등의 테스트
        return new File(path+"/safeme/"+fileName+".mp4");
    }


    public static String readData(BufferedReader br) throws IOException {
        String line = "";
        StringBuilder sb = new StringBuilder();

        while ((line = br.readLine()) != null) {
            sb.append(line.trim());
        }

        return sb.toString();
    }
}
