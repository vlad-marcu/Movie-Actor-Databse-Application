package fileio;

public class Series {
    private double rating;

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getNo_season() {
        return no_season;
    }

    public void setNo_season(int no_season) {
        this.no_season = no_season;
    }

    private int no_season;

    public Series(double rating,int no_season){
        this.rating=rating;
        this.no_season=no_season;
    }



}
