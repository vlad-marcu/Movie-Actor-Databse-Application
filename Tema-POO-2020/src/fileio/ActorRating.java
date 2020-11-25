package fileio;

public class ActorRating {
    private String actor_name;
    private double actor_rating;

    public char getActorFirstLetter(){
        return actor_name.charAt(0);
    }

    public String getActor_name() {
        return actor_name;
    }

    public void setActor_name(String actor_name) {
        this.actor_name = actor_name;
    }

    @Override
    public String toString() {
        return "ActorRating{" +
                "actor_name='" + actor_name + '\'' +
                ", actor_rating=" + actor_rating +
                '}';
    }

    public double getActor_rating() {
        return actor_rating;
    }

    public void setActor_rating(double actor_rating) {
        this.actor_rating = actor_rating;
    }

    public ActorRating(String actor_name, double actor_rating){
        this.actor_name=actor_name;
        this.actor_rating=actor_rating;
    }
}
