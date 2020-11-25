package fileio;

import java.util.ArrayList;

public class TvShow {
    public ArrayList<Series> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<Series> seasons) {
        this.seasons = seasons;
    }

    ArrayList<Series> seasons=new ArrayList<Series>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean alreadyRated(int number){
        for(int i=0;i<seasons.size();i++){
            if(seasons.get(i).getNo_season() == number)
                return true;
        }
        return false;
    }

    private String title;
    //Returneaza ratingul unui sezon
    public double getSeriesRating(int idx){
    for(int i=0;i<getSeasons().size();i++){
        if(getSeasons().get(i).getNo_season() == idx)
            return getSeasons().get(i).getRating();
    }
    return 0;
    }
}
