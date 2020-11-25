package fileio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * General information about show (video), retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public abstract class                                                                                                                                                                                 ShowInput {
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

    public int getViews(List<UserInputData> users){
        int views=0;
        for(int i=0;i<users.size();i++) {
            if(users.get(i).getHistory().containsKey(title)){
                views += users.get(i).getHistory().get(title);
            }


        }
        return views;
    }

    public boolean checkFilters(ActionInputData action){
        if(action.getFilters().get(1).get(0) != null) {
            for (int i = 0; i < action.getFilters().get(1).size(); i++) {
                if (genres.contains(action.getFilters().get(1).get(i)) == false)
                    return false;
            }
        }
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(year);
        if(action.getFilters().get(0).get(0) != null) {
            if (stringBuilder.toString().equals(action.getFilters().get(0).get(0)) == false)
                return false;
        }
        return true;

    }

    public int getFavourites(List<UserInputData> users){
        int no_of_favourites=0;
        for(int i=0;i<users.size();i++){
            if(users.get(i).getFavoriteMovies().contains(title))
                no_of_favourites++;
        }
        return no_of_favourites;
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
