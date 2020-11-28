package fileio;

import entertainment.Genre;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * Information about an user, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class UserInputData {


    /**
     * User's username
     */
    private final String username;
    /**
     * Subscription Type
     */
    private final String subscriptionType;
    /**
     * The history of the movies seen
     */
    private final Map<String, Integer> history;
    /**
     * Movies added to favorites
     */
    private final ArrayList<String> favoriteMovies;

    private HashMap<String, Double> movieRatings = new HashMap<String, Double>();

    private ArrayList<TvShow> showRatings = new ArrayList<TvShow>();

    public ArrayList<TvShow> getShowRatings() {
        return showRatings;
    }

    public void setShowRatings(final ArrayList<TvShow> showRatings) {
        this.showRatings = showRatings;
    }

    public HashMap<String, Double> getMovieRatings() {
        return movieRatings;
    }

    public void setMovieRatings(final HashMap<String, Double> movieRatings) {
        this.movieRatings = movieRatings;
    }

    public UserInputData(final String username, final String subscriptionType,
                         final Map<String, Integer> history,
                         final ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.favoriteMovies = favoriteMovies;
        this.history = history;
    }

    public String getUsername() {
        return username;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    /**
     *
     * @return return how many ratings an user has given
     */
    public int getNoRatings() {
        int noofratings = 0;
        noofratings += movieRatings.size();
        for (int i = 0; i < showRatings.size(); i++) {
            noofratings += showRatings.get(i).getSeasons().size();
        }
        return noofratings;
    }

    /**
     *
     * @param string genre
     * @param movies all of the movies
     * @param serials all of the serials
     * @param users all of the users
     * @return returns a list of unviewed videos from a genre
     */
    public ArrayList<ShowSearch> getSearch(final String string, final List<MovieInputData> movies,
                                           final List<SerialInputData> serials,
                                           final List<UserInputData> users) {
        ArrayList<ShowSearch> shows = new ArrayList<ShowSearch>();
        for (int i = 0; i < movies.size(); i++) {
            if (!this.getHistory().containsKey(movies.get(i).getTitle())) {
                if (movies.get(i).getGenres().contains(string)) {
                        ShowSearch show = new ShowSearch(movies.get(i).getTitle(),
                                movies.get(i).getMovieRating(users));
                        shows.add(show);
                }
            }
        }
        for (int j = 0; j < movies.size(); j++) {
            if (!this.getHistory().containsKey(serials.get(j).getTitle())) {
                if (serials.get(j).getGenres().contains(string)) {
                    ShowSearch show = new ShowSearch(serials.get(j).getTitle(),
                            serials.get(j).getShowRating(users));
                    shows.add(show);
                }
            }
        }
        return shows;

    }

    /**
     *
     * @param action current command
     * @param writer the writer we use to write the JSON object
     * @return adds video to faovurite list if possible then writes JSON object
     * @throws IOException
     */
    public JSONObject getFavorite(final ActionInputData action,
                                  final Writer writer) throws IOException {

        for (Map.Entry mapElement : history.entrySet()) {
            if (mapElement.getKey().equals(action.getTitle())) {
                if (favoriteMovies.contains(action.getTitle())) {
                    return writer.writeFile(action.getActionId(), null, "error ->"
                            + ' '
                            + action.getTitle()
                            + ' '
                            + "is already in favourite list");
                } else {
                    favoriteMovies.add(action.getTitle());
                    return writer.writeFile(action.getActionId(), null, "success ->"
                            + ' '
                            + action.getTitle()
                            + ' '
                            + "was added as favourite");
                }
            }



        }
 return writer.writeFile(action.getActionId(), null, "error ->"
                + ' '
                + action.getTitle()
                + ' '
                + "is not seen");

    }

    /**
     *
     * @param movies all of the movies
     * @param genre the genre
     * @param shows all the shows
     * @param action the current action
     * @param writer the writer that writes the JSON object
     * @return checks if videos are viewed then if they contain the genre then writes JSON object
     * @throws IOException
     */


    /**
     *
     * @param movies all of the movies
     * @param shows all of the shows
     * @param action the current command
     * @param writer the writer that writes the JSON object
     * @return checks if video is viewed then writes JSON object
     * @throws IOException
     */
    public JSONObject getStandard(final List<MovieInputData> movies,
                                  final List<SerialInputData> shows,
                                  final ActionInputData action,
                                  final Writer writer)throws IOException {
        for (int i = 0; i < movies.size(); i++) {
            if (!history.containsKey(movies.get(i).getTitle())) {
                return writer.writeFile(action.getActionId(), null, "StandardRecommendation result:"
                        + ' '
                        + movies.get(i).getTitle());
            }
        }
        for (int i = 0; i < shows.size(); i++) {
            if (!history.containsKey(shows.get(i).getTitle())) {
                return writer.writeFile(action.getActionId(),
                        null, "StandardRecommendation result:"
                                + shows.get(i).getTitle());
            }
        }
        return null;
    }

    /**
     *
     * @param action the current command
     * @param writer the writer that writes the JSON object
     * @return updates the number of views of an user for a given video
     * @throws IOException
     */
    public JSONObject getView(final ActionInputData action,
                              final Writer writer) throws IOException {
        for (Map.Entry mapElement : history.entrySet()) {
            if (mapElement.getKey().equals(action.getTitle())) {

                mapElement.setValue((int) mapElement.getValue() + 1);
                return writer.writeFile(action.getActionId(), null,
                        "success ->"
                                + ""
                                + ' '
                                + action.getTitle()
                                + ' '
                                + "was viewed with total views of"
                                + ' '
                                + mapElement.getValue());
            }
        }
        history.put(action.getTitle(), 1);
        return writer.writeFile(action.getActionId(), null,
                "success ->"
                        + ""
                        + ' '
                        + action.getTitle()
                        + ' '
                        + "was viewed with total views of"
                        + ' '
                        + 1);
    }

    /**
     *
     * @param action the current command
     * @param shows all of the shows
     * @param writer the writer that writes the JSON object
     * @return  updates the rating of a video then writes JSON object
     * @throws IOException
     */
    public JSONObject getRating(final ActionInputData action,
                                final List<SerialInputData> shows,
                                final Writer writer) throws IOException {
        for (Map.Entry mapElement : history.entrySet()) {
            if (mapElement.getKey().equals(action.getTitle())) {
                if (action.getSeasonNumber() == 0) {
                    if (movieRatings.containsKey(action.getTitle())) {
                        return writer.writeFile(action.getActionId(),
                                null, "error ->"
                                        + ' '
                                        + action.getTitle()
                                        + ' '
                                        + "has been already rated"
                        );
                    }
                    movieRatings.put(action.getTitle(), action.getGrade());
                    return writer.writeFile(action.getActionId(),
                            null, "success ->"
                                    + ' '
                                    + action.getTitle()
                                    + ' '
                                    + "was rated with"
                                    + ' '
                                    + movieRatings.get(action.getTitle())
                                    + ' '
                                    + "by"
                                    + ' '
                                    + action.getUsername());

                } else {
                    for (int i = 0; i < showRatings.size(); i++) {
                        if (showRatings.get(i).getTitle().equals(action.getTitle())) {
                            if (showRatings.get(i).alreadyRated(action.getSeasonNumber())) {
                                return writer.writeFile(action.getActionId(),
                                        null, "error ->"
                                                + ' '
                                                + action.getTitle()
                                                + ' '
                                                + "has been already rated"
                                );
                            }
                        }
                    }
                    Series series = new Series(action.getGrade(), action.getSeasonNumber());
                    TvShow show = new TvShow();
                    show.setTitle(action.getTitle());
                    show.getSeasons().add(series);
                    showRatings.add(show);
                    return writer.writeFile(action.getActionId(),
                            null, "success ->"
                                    + ' '
                                    + action.getTitle()
                                    + ' '
                                    + "was rated with"
                                    + ' '
                                    + showRatings.get(showRatings.size() - 1)
                                    .getSeriesRating(action.getSeasonNumber())
                                    + ' '
                                    + "by"
                                    + ' ' + action.getUsername());
                }
            }
        }
        return writer.writeFile(action.getActionId(), null, "error ->"
                + ' '
                + action.getTitle()
                + ' '
                + "is not seen");


    }


    @Override
    public String toString() {
        return "UserInputData{"
                + "username='"
                + username
                + '\''
                + ", subscriptionType='"
                + subscriptionType
                + '\''
                + ", history="
                + history
                + ", favoriteMovies="
                + favoriteMovies
                + ", showRatings="
                + showRatings
                + ", movieRatings="
                + movieRatings
                + '}';
    }
}
