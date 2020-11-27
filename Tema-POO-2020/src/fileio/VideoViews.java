package fileio;

public final class VideoViews {
    private String videoname;
    private int views;
    public char getFirstLetter() {
        return videoname.charAt(0);
    }
    public VideoViews(final String videoname, final int views) {
        this.videoname = videoname;
        this.views = views;
    }

    public String getVideoname() {
        return videoname;
    }

    public void setVideoname(final String videoname) {
        this.videoname = videoname;
    }

    public int getViews() {
        return views;
    }

    public void setViews(final int views) {
        this.views = views;
    }
}
