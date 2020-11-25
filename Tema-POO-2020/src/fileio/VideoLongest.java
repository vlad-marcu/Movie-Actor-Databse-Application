package fileio;

public class VideoLongest {
    private String video_name;
    private int duration;

    public VideoLongest(String video_name,int duration){
        this.video_name=video_name;
        this.duration=duration;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public char getFirstLetter(){
        return video_name.charAt(0);
    }
}
