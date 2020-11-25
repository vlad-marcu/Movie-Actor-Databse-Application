package fileio;

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

    private ArrayList<TvShow> showRatings=new ArrayList<TvShow>();

    public ArrayList<TvShow> getShowRatings() {
        return showRatings;
    }

    public void setShowRatings(ArrayList<TvShow> showRatings) {
        this.showRatings = showRatings;
    }

    public HashMap<String, Double> getMovieRatings() {
        return movieRatings;
    }

    public void setMovieRatings(HashMap<String, Double> movieRatings) {
        this.movieRatings = movieRatings;
    }

    private HashMap<String,Double> movieRatings=new HashMap<String, Double>();


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

    public int getNoRatings(){
        int no_of_ratings=0;
        no_of_ratings += movieRatings.size();
        for(int i=0;i<showRatings.size();i++)
        {
            no_of_ratings += showRatings.get(i).getSeasons().size();
        }
        return no_of_ratings;
    }

    public JSONObject getFavorite(ActionInputData action, Writer writer) throws IOException {

        for(Map.Entry mapElement : history.entrySet()){
            if(mapElement.getKey().equals(action.getTitle())){
                if(favoriteMovies.contains(action.getTitle())==true) {
                    return writer.writeFile(action.getActionId(), null, "error ->" +
                            ' ' + action.getTitle() + ' ' +
                            "is already in favourite list");
                }
            else {
                    favoriteMovies.add(action.getTitle());
                    return writer.writeFile(action.getActionId(), null, "success ->" +
                            ' ' + action.getTitle() + ' '
                            + "was added as favourite");
                }
            }



        }
 return writer.writeFile(action.getActionId(),null,"error ->"+
                ' '+action.getTitle()+' '
                +"is not seen");

    }

    public JSONObject getView(ActionInputData action,Writer writer) throws IOException{
        for(Map.Entry mapElement : history.entrySet()){
            if(mapElement.getKey().equals(action.getTitle())){

                mapElement.setValue((int)mapElement.getValue()+1);
                return writer.writeFile(action.getActionId(),null,"success ->"+' '+action.getTitle()+' '+"was viewed with total views of"+' '+mapElement.getValue());
            }
        }
        history.put(action.getTitle(),1);
        return writer.writeFile(action.getActionId(),null,"success ->"+' '+action.getTitle()+' '+"was viewed with total views of"+' '+1);
    }
    public JSONObject getRating(ActionInputData action,List<SerialInputData> shows,Writer writer) throws IOException{
        for(Map.Entry mapElement : history.entrySet()){
            if(mapElement.getKey().equals(action.getTitle())) {
                if (action.getSeasonNumber() == 0) {
                    if(movieRatings.containsKey(action.getTitle()))
                        return writer.writeFile(action.getActionId(), null, "error ->"+' '+ action.getTitle()+' '+"has been already rated"
                                );
                    movieRatings.put(action.getTitle(), action.getGrade());
                    return writer.writeFile(action.getActionId(), null, "success ->" + ' ' + action.getTitle() + ' ' + "was rated with" + ' ' +
                            movieRatings.get(action.getTitle()) + ' ' + "by" + ' ' +
                            action.getUsername());

                } else {

                                for(int i=0;i<showRatings.size();i++){
                                    if(showRatings.get(i).getTitle().equals(action.getTitle())){
                                        if(showRatings.get(i).alreadyRated(action.getSeasonNumber()))
                                            return writer.writeFile(action.getActionId(), null, "error ->"+' '+ action.getTitle()+' '+"has been already rated"
                                            );
                                    }
                                }
                                Series series = new Series(action.getGrade(), action.getSeasonNumber());
                                TvShow show = new TvShow();
                                show.setTitle(action.getTitle());
                                show.getSeasons().add(series);
                                showRatings.add(show);
                                return writer.writeFile(action.getActionId(), null, "success ->" + ' ' + action.getTitle() + ' ' + "was rated with" + ' ' +
                                        showRatings.get(showRatings.size()-1).getSeriesRating(action.getSeasonNumber()) + ' ' + "by" + ' ' +
                                        action.getUsername());





                }
            }
        }
        return writer.writeFile(action.getActionId(),null,"error ->"+
                ' '+action.getTitle()+' '
                +"is not seen");


    }



    @Override
    public String toString() {
        return "UserInputData{" + "username='"
                + username + '\'' + ", subscriptionType='"
                + subscriptionType + '\'' + ", history="
                + history + ", favoriteMovies="
                + favoriteMovies + '}';
    }
}
