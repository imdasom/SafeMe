package safeme.com.naver.cafe.safeme.obj;

/**
 * Created by dasom on 2017-05-09.
 */
public class PoliceAddress {
    private String id;
    private String loc_str;

    public PoliceAddress(String id, String loc_str) {
        this.id = id;
        this.loc_str = loc_str;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoc_str() {
        return loc_str;
    }

    public void setLoc_str(String loc_str) {
        this.loc_str = loc_str;
    }

    @Override
    public String toString() {
        return "PoliceAddress{" +
                "id='" + id + '\'' +
                ", loc_str='" + loc_str + '\'' +
                '}';
    }
}
