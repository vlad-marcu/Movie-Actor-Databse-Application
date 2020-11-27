package fileio;

public final class ActorAward {
    private int noofawards;
    private String actorname;
    public ActorAward(final int noofawards, final String actorName) {
        this.noofawards = noofawards;
        this.actorname = actorName;
    }

    public int getNoofawards() {
        return noofawards;
    }

    @Override
    public String toString() {
        return "Actor_Award{"
                + "no_of_awards="
                + noofawards
                + ", actor_name='"
                + actorname
                + '\''
                + '}';
    }


    public void setNoofawards(final int noofawards) {
        this.noofawards = noofawards;
    }

    public String getActorname() {
        return actorname;
    }

    public void setActorname(final String actorname) {
        this.actorname = actorname;
    }

    public char getActorFirstLetter() {
        return actorname.charAt(0);
    }
    public char getActorSecondLetter() {
        return actorname.charAt(1);
    }

}
