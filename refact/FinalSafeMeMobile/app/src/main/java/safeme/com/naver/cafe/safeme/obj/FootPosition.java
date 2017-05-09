package safeme.com.naver.cafe.safeme.obj;

/**
 * Created by dasom on 2017-05-09.
 */
public class FootPosition {
    private String id;
    private String lat;
    private String lon;

    public FootPosition(String id, String lat, String lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
