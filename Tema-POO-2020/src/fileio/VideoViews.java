package fileio;

public class VideoViews {
    private String video_name;
    private int views;
    public char getFirstLetter(){
        return video_name.charAt(0);
    }
    public VideoViews(String video_name,int views){
        this.video_name=video_name;
        this.views=views;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
