package fileio;

public final class ActorRating {
    private String actorName;
    private double actorRating;

    public char getActorFirstLetter() {

        return actorName.charAt(0);
    }

    public char getActorSecondLetter() {
        return actorName.charAt(1);
    }


    public String getActorName() {
        return actorName;
    }

    public void setActorName(final String actorname) {
        this.actorName = actorname;
    }

    @Override
    public String toString() {
        return "ActorRating{"
                + "actor_name='"
                + actorName
                + '\''
                + ", actor_rating="
                + actorRating
                + '}';
    }

    public double getActorRating() {
        return actorRating;
    }

    public void setActoRating(final double actorrating) {
        this.actorRating = actorrating;
    }

    public ActorRating(final String actorName, final double actorRating) {
        this.actorName = actorName;
        this.actorRating = actorRating;
    }
}
