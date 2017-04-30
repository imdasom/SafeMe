package safeme.com.naver.cafe.safeme.video;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 파일 관련 일을 하는 클래스입니다.
 */
public class MyFile {

    protected static File file;
    protected static String fileName;
    protected static String pathName;

    public MyFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory().getPath()+"/safeme/");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                file = null;
            }
        }

        // Create a media file name
        fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        pathName = mediaStorageDir.getPath() + File.separator + fileName;
    }

    public File getFile(){ return file; }
    public String getFileName(){
        return fileName;
    }
    public String getPathName() { return pathName; }

}
