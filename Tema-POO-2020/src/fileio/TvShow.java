package fileio;

import java.util.ArrayList;

public final class TvShow {

   private ArrayList<Series> seasons = new ArrayList<Series>();
    public ArrayList<Series> getSeasons() {
        return seasons;
    }

    public void setSeasons(final ArrayList<Series> seasons) {
        this.seasons = seasons;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     *
     * @param number nomber of a season
     * @return returns true if the season is rated and false otherwise
     */
    public boolean alreadyRated(final int number) {
        for (int i = 0; i < seasons.size(); i++) {
            if (seasons.get(i).getNoseason() == number) {
                return true;
            }
        }
        return false;
    }

    private String title;

    /**
     *
     * @param idx number of season
     * @return returns the rating of a season
     */
    public double getSeriesRating(final int idx) {
    for (int i = 0; i < getSeasons().size(); i++) {
        if (getSeasons().get(i).getNoseason() == idx) {
            return getSeasons().get(i).getRating();
        }
    }
    return 0;
    }
}
