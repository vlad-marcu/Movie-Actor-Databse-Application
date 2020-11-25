package fileio;

public class VideoRating {

    private String video_name;
    private double video_rating;

    public VideoRating(String video_name, double video_rating) {
        this.video_name = video_name;
        this.video_rating = video_rating;
    }


    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public double getVideo_rating() {
        return video_rating;
    }

    public void setVideo_rating(double video_rating) {
        this.video_rating = video_rating;
    }

    public char getFirstLetter(){
        return video_name.charAt(0);
    }
}

