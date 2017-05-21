package safeme.com.naver.cafe.safeme.map.util;

/**
 * Created by dasom on 2017-05-21.
 */
public class UserLocation {
    private double latitude;
    private double longitude;

    public UserLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean checkValidation() {
        if(latitude > 0.0f && longitude > 0.0f) {
            return true;
        } else {
            return false;
        }
    }
}
