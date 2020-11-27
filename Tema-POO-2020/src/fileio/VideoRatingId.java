package fileio;

public final class VideoRatingId {

        private String videoname;
        private double videorating;
        private int index;
        public VideoRatingId(final String videoname, final double videorating,
                             final int index) {
            this.videoname = videoname;
            this.videorating = videorating;
            this.index = index;
        }

    public int getIndex() {
        return index;
    }

    public void setIndex(final int index) {
        this.index = index;
    }

    public String getVideoname() {
            return videoname;
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
