package safeme.com.naver.cafe.safeme.video;

/**
 * Created by dasom on 2017-05-09.
 */
public class VideoActivityFlag {

    private boolean isPressedBack;
    private boolean isUserInfoSended;
    private boolean startedSending;

    public VideoActivityFlag() {
        this.isPressedBack = false;
        this.isUserInfoSended = false;
        this.startedSending = false;
    }

    public boolean isPressedBack() {
        return isPressedBack;
    }

    public void setIsPressedBack(boolean isPressedBack) {
        this.isPressedBack = isPressedBack;
    }

    public boolean isUserInfoSended() {
        return isUserInfoSended;
    }

    public void setIsUserInfoSended(boolean isUserInfoSended) {
        this.isUserInfoSended = isUserInfoSended;
    }

    public boolean isStartedSending() {
        return startedSending;
    }

    public void setStartedSending(boolean startedSending) {
        this.startedSending = startedSending;
    }
}
