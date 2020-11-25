package fileio;

public class VideoFavorite {
    private String video_name;
    private int no_of_favourites;
    public VideoFavorite(String video_name,int no_of_favourites){
        this.video_name=video_name;
        this.no_of_favourites=no_of_favourites;
    }

    public char getFirstLetter(){
        return video_name.charAt(0);
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public int getNo_of_favourites() {
        return no_of_favourites;
    }

    public void setNo_of_favourites(int no_of_favourites) {
        this.no_of_favourites = no_of_favourites;
    }
}
