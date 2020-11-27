package fileio;

public final class VideoRating {

    private String videoname;
    private double videorating;

    public VideoRating(final String videoname, final double videorating) {
        this.videoname = videoname;
        this.videorating = videorating;
    }


    public String getVideoname() {
        return videoname;
    }

    @Override
    public String toString() {
        return "VideoRating{"
                + "video_name='"
                + videoname
                + '\''
                + ", video_rating="
                + videorating
                + '}';
    }

    public void setVideoname(final String videoname) {
        this.videoname = videoname;
    }

    public double getVideorating() {
        return videorating;
    }

    public void setVideorating(final double videorating) {
        this.videorating = videorating;
    }

    public char getFirstLetter() {
        return videoname.charAt(0);
    }
}

