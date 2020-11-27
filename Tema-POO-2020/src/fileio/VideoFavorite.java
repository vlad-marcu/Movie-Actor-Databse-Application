package fileio;

public final class VideoFavorite {
    private String videoname;
    private int nooffavourites;
    public VideoFavorite(final String videoname, final int nooffavourites) {
        this.videoname = videoname;
        this.nooffavourites = nooffavourites;
    }

    public char getFirstLetter() {
        return videoname.charAt(0);
    }

    public String getVideoname() {
        return videoname;
    }

    public void setVideoname(final String videoname) {
        this.videoname = videoname;
    }

    public int getNooffavourites() {
        return nooffavourites;
    }

    public void setNooffavourites(final int nooffavourites) {
        this.nooffavourites = nooffavourites;
    }
}
