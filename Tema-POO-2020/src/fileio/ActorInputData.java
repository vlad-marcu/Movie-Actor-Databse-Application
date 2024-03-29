package fileio;

import actor.ActorsAwards;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Information about an actor, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class ActorInputData {
    /**
     * actor name
     */
    private String name;
    /**
     * description of the actor's career
     */
    private String careerDescription;
    /**
     * videos starring actor
     */
    private ArrayList<String> filmography;
    /**
     * awards won by the actor
     */
    private Map<ActorsAwards, Integer> awards;

    public ActorInputData(final String name, final String careerDescription,
                          final ArrayList<String> filmography,
                          final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(final ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public void setCareerDescription(final String careerDescription) {
        this.careerDescription = careerDescription;
    }

    /**
     *
     * @param users all the users
     * @param movies all the movies
     * @param serials all the serials
     * @return total rating of an actor
     */
    public double getActorRating(final List<UserInputData>  users,
                                 final List<MovieInputData> movies,
                                 final List<SerialInputData> serials) {
        double actorRating = 0;
        double productions = 0;
       for (int i = 0; i < this.filmography.size(); i++) {
           double showRating = 0;
           for (int j = 0; j < movies.size(); j++) {
               if (movies.get(j).getTitle().equals(this.filmography.get(i))) {

                   showRating += movies.get(j).getMovieRating(users);

                   if (movies.get(j).getMovieRating(users) != 0) {
                       productions++;
                   }

               }
           }
       for (int k = 0; k < serials.size(); k++) {
               if (serials.get(k).getTitle().equals(this.filmography.get(i))) {
                   showRating += serials.get(k).getShowRating(users);

                   if (serials.get(k).getShowRating(users) != 0) {
                       productions++;
                   }
               }
           }
            actorRating = actorRating + showRating;
       }
             if (productions != 0) {
                 return actorRating / productions;

             } else {
                 return 0;
             }
    }
    public char getFirstLetter() {
        return name.charAt(0);
    }

    public char getSecondLetter() {
        return name.charAt(1);
    }

    /**
     *
     * @param action the command that is given
     * @return number of awards of an actor respecting the parameters
     */
    public int getAwardsNumber(final ActionInputData action) {
        int noofawards = 0;
        int index = 3;
        for (int i = 0; i < action.getFilters().get(index).size(); i++) {
            if (action.getFilters().get(index).get(i) != null) {
                ActorsAwards award = ActorsAwards.valueOf(action.getFilters().get(index).get(i));
                if (!awards.containsKey(award)) {
                    return 0;
                }
            }
        }
        for (Map.Entry mapElement : awards.entrySet()) {
           noofawards = noofawards + (int) mapElement.getValue();
        }
        return noofawards;
    }

    /**
     *
     * @param action the command that is given
     * @return true if the actor contains the given keywords, false otherwise
     */
    public boolean checkActorKeyWords(final ActionInputData action) {
        for (int i = 0; i < action.getFilters().get(2).size(); i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(action.getFilters().get(2).get(i));
            stringBuilder.append(" ");
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(action.getFilters().get(2).get(i));
            stringBuilder2.append(",");
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append(action.getFilters().get(2).get(i));
            stringBuilder3.append(".");
            if (!careerDescription.toLowerCase().contains(stringBuilder.toString())) {
                if (!careerDescription.toLowerCase().contains(stringBuilder2.toString())) {
                    if (!careerDescription.toLowerCase().contains(stringBuilder3.toString())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "ActorInputData{"
                + "name='" + name
                + '\''
                + ", descriptions="
                + careerDescription
                + '}';
    }
}
