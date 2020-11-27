package fileio;

import java.util.ArrayList;
import java.util.List;

/**
 * Information about a movie, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class MovieInputData extends ShowInput {
    /**
     * Duration in minutes of a season
     */
    private final int duration;

    /**
     *
     * @param users all of the users
     * @return returns the rating of a movie
     */
    public double getMovieRating(final List<UserInputData> users) {
        double rating = 0;
        double noofusers = 0;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getMovieRatings().containsKey(this.getTitle())) {
                rating += users.get(i).getMovieRatings().get(this.getTitle());
                noofusers++;
            }
        }
        if (noofusers == 0) {
            return 0;
        } else {
            return rating / noofusers;
        }
    }

    public MovieInputData(final String title, final ArrayList<String> cast,
                          final ArrayList<String> genres, final int year,
                          final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "MovieInputData{"
                + "title= "
                + super.getTitle()
                + ' '
                + "year= "
                + super.getYear()
                + "duration= "
                + duration
                + "cast {"
                + super.getCast()
                + " }\n"
                + "genres {"
                + super.getGenres()
                + " }\n ";
    }
}
