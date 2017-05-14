package safeme.com.naver.cafe.safeme.video.util;

/**
 * Created by dasom on 2017-05-14.
 */
public class UserInfo {
    private String[] phonenums;
    private String fileName;
    private String address;
    private double lat;
    private double lon;

    private static final String _phonenum = "pnum";
    private static final String _fileName = "fname";
    private static final String _address = "str";
    private static final String _lat= "lat";
    private static final String _lon = "lon";

    public UserInfo(String[] phonenum, String fileName, String address, double lat, double lon) {
        this.phonenums = phonenum;
        this.fileName = fileName;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
    }

    public String[] getPhonenums() {
        return phonenums;
    }

    public void setPhonenums(String[] phonenums) {
        this.phonenums = phonenums;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    private String spaceToLetter(String str) {
        String[] temp = str.split(" ");
        String result = "";
        for (int i = 0; i < temp.length - 1; i++) {
            result = result + temp[i] + "%20";
        }
        result = result + temp[temp.length - 1];
        return result;
    }

    public String getPostData() {
        return "?"
                + _phonenum + "=" + phonenums[0]    // 일단 한개만 보내도록 한다.
                + "&" + _fileName + "=" + fileName
                + "&" + _address + "=" + spaceToLetter(address)
                + "&" + _lat + "=" + lat
                + "&" + _lon + "=" + lon;
    }
}
