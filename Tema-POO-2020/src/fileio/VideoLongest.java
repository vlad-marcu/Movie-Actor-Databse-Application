package fileio;

public final class VideoLongest {
    private String videoname;
    private int duration;

    public VideoLongest(final String videoname, final int duration) {
        this.videoname = videoname;
        this.duration = duration;
    }

    public String getVideoname() {
        return videoname;
    }

    public void setVideoname(final String videoname) {
        this.videoname = videoname;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public char getFirstLetter() {
        return videoname.charAt(0);
    }
}
