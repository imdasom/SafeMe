package safeme.com.naver.cafe.safeme.obj;

/**
 * Created by dasom on 2017-05-09.
 */
public class PoliceData {
    private String id;
    private String loc_lan;
    private String loc_lon;
    private String loc_str;

    public PoliceData(String id, String loc_lan, String loc_lon, String loc_str) {
        this.id = id;
        this.loc_lan = loc_lan;
        this.loc_lon = loc_lon;
        this.loc_str = loc_str;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoc_lan() {
        return loc_lan;
    }

    public void setLoc_lan(String loc_lan) {
        this.loc_lan = loc_lan;
    }

    public String getLoc_lon() {
        return loc_lon;
    }

    public void setLoc_lon(String loc_lon) {
        this.loc_lon = loc_lon;
    }

    public String getLoc_str() {
        return loc_str;
    }

    public void setLoc_str(String loc_str) {
        this.loc_str = loc_str;
    }
}
