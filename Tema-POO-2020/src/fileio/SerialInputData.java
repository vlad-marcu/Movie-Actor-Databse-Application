package fileio;

import entertainment.Season;

import java.util.ArrayList;
import java.util.List;

/**
 * Information about a tv show, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class SerialInputData extends ShowInput {
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private final ArrayList<Season> seasons;

    /**
     *
     * @return the duration of a show
     */
    public int getShowDuration() {
        int duration = 0;
        for (int i = 0; i < seasons.size(); i++) {
            duration += seasons.get(i).getDuration();
        }
        return duration;
    }

    /**
     *
     * @param users all of the users
     * @return returns the rating of a show
     */
    public double getShowRating(final List<UserInputData> users) {
        double showRating = 0;
        for (int i = 1; i <= numberOfSeasons; i++) {
            double seasonRating = 0;
            double noofusers = 0;

            for (int j = 0; j < users.size(); j++) {
                for (int k = 0; k < users.get(j).getShowRatings().size(); k++) {
                    if (users.get(j).getShowRatings().get(k).
                            getTitle().equals(this.getTitle())) {
               seasonRating = seasonRating
                       + users.get(j).getShowRatings().get(k).getSeriesRating(i);
                   if (users.get(j).getShowRatings().get(k).getSeriesRating(i) != 0) {
                       noofusers++;
                   }

                    }
                }

            }
            if (noofusers != 0) {
                showRating = showRating + seasonRating / noofusers;
            }



        }
        showRating = showRating / (double) numberOfSeasons;
        return showRating;
                }



    public SerialInputData(final String title, final ArrayList<String> cast,
                           final ArrayList<String> genres,
                           final int numberOfSeasons, final ArrayList<Season> seasons,
                           final int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
    }



    public int getNumberSeason() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    @Override
    public String toString() {
        return "SerialInputData{" + " title= "
                + super.getTitle() + " " + " year= "
                + super.getYear() + " cast {"
                + super.getCast() + " }\n" + " genres {"
                + super.getGenres() + " }\n "
                + " numberSeason= " + numberOfSeasons
                + ", seasons=" + seasons + "\n\n" + '}';
    }
}
