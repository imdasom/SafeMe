package safeme.com.naver.cafe.safeme.constants;

import safeme.com.naver.cafe.safeme.MainActivity;

/**
 * Created by safeme on 2015-12-02.
 */
public class Constants {
    // using in appconfig
    //final public static String STREAM_ADDR = "192.168.0.23:1935";
    //final public static String PARSING_ADDR = "192.168.0.23:8080";
    final public static String PARSING_ADDR = "dasomhome.iptime.org:8080";
    final public static String STREAM_ADDR = "192.168.0.6:1935";
    final public static String PARSING_URL = "http://"+PARSING_ADDR+"/SafeMe/";
    final public static String STREAM_URL = "rtsp://"+STREAM_ADDR+"/live/myStream";
    final public static String PARSING_DIR_VIDEO = "backpage/Video_uploadFiles.jsp";
    final public static String PARSIN_DIR_GET_SERVER_IP = "backpage/Server_getServerIp.jsp";
    final public static String PARSING_DIR_GETPOLICEPOS = "backpage/Policepos_getPos.jsp";
    final public static String PARSING_DIR_GETFOOTPOS = "backpage/Footpos_getPos.jsp";
    final public static String PARSING_DIR_UPDATEPOLICEPOSLATLNG = "backpage/Policepos_updateDB.jsp";
    final public static String PARSING_DIR_GETPOLICEPOSADDR = "backpage/Policepos_sendAddress.jsp";
    final public static String PARSING_DIR_NOFIFYINFO = "backpage/Notify_updateDB.jsp";
    final public static String PUBLISHER_USERNAME = "imdasom";
    final public static String PUBLISHER_PASSWORD = "1109";

    // using in map
    final public static int REMOVE_MARKER = 11;
    final public static int ON_LOCATION_CHANGED = 12;

    // using in json parsing
    final public static int SEND_VIDEOFILE = 20;
    final public static int SEND_NOFITYINFO = 21;
    final public static int SEND_POLICE_LATLNG = 22;
    final public static int GET_POLICE_ADDRESS = 23;
    final public static int GET_SERVER_IP = 24;
    final public static int GET_POLICEPOS_DATA = 25;
    final public static int GET_FOOTPOS_DATA = 26;

    final public static int PARSING_START = 31;
    final public static int PARSING_ONPROGRESS = 32;
    final public static int PARSING_FINISH = 33;
    final public static int PARSING_ERROR = 34;

    // using in notify
    final public static int STREAM_ERROR = 40;
    final public static int RECORD_FINISH = 41;
    final public static int SENDING_FINISH = 42;
    final public static int FINISH_VIDEOACTIVITY = 43;
    final public static int SENDING_VIDEO = 44;
    final public static int ALL_FINISH = 45;
}

