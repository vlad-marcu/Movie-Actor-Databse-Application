package fileio;

import java.util.ArrayList;
import java.util.List;


/**
 * General information about show (video), retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
    public abstract class                                                                                                                                             ShowInput {
    /**
     * Show's title
     */
    private final String title;
    /**
     * The year the show was released
     */
    private final int year;
    /**
     * Show casting
     */
    private final ArrayList<String> cast;
    /**
     * Show genres
     */
    private final ArrayList<String> genres;

    public ShowInput(final String title, final int year,
                     final ArrayList<String> cast, final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
    }

    /**
     *
     * @param users all of the users
     * @return return the number of views
     */
    public int getViews(final List<UserInputData> users) {
        int views = 0;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getHistory().containsKey(title)) {
                views += users.get(i).getHistory().get(title);
            }


        }
        return views;
    }

    /**
     *
     * @param users list of all users
     * @param user the user to skip
     * @return return the number of times the show is in a favourite list
     */
    public int getFavoriteRating(final List<UserInputData> users, final UserInputData user) {
        int favorite = 0;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i) != user) {
                if (users.get(i).getFavoriteMovies().contains(this.getTitle())) {
                    favorite++;
                }
            }
        }
        return favorite;
    }

    /**
     *
     * @param action the current commnad
     * @return checks if a show has all the filters
     */
    public boolean checkFilters(final ActionInputData action) {
        if (action.getFilters().get(1).get(0) != null) {
            for (int i = 0; i < action.getFilters().get(1).size(); i++) {
                if (!genres.contains(action.getFilters().get(1).get(i))) {
                    return false;
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(year);
        if (action.getFilters().get(0).get(0) != null) {
            if (!stringBuilder.toString().equals(action.getFilters().get(0).get(0))) {
                return false;
            }
        }
        return true;

    }

    /**
     *
     * @param users all of the users
     * @return returns the number of times a movie appears in a favourite list
     */
    public int getFavourites(final List<UserInputData> users) {
        int nooffavourites = 0;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getFavoriteMovies().contains(title)) {
                nooffavourites++;
            }
        }
        return nooffavourites;
    }

    public final String getTitle() {
        return title;
    }

    public final int getYear() {
        return year;
    }

    public final ArrayList<String> getCast() {
        return cast;
    }

    public final ArrayList<String> getGenres() {
        return genres;
    }
}
