package fileio;

public class Actor_Award {
    private int no_of_awards;
    private String actor_name;
    public Actor_Award(int no_of_awards,String actor_name){
        this.no_of_awards=no_of_awards;
        this.actor_name=actor_name;
    }

    public int getNo_of_awards() {
        return no_of_awards;
    }

    @Override
    public String toString() {
        return "Actor_Award{" +
                "no_of_awards=" + no_of_awards +
                ", actor_name='" + actor_name + '\'' +
                '}';
    }

    public void setNo_of_awards(int no_of_awards) {
        this.no_of_awards = no_of_awards;
    }

    public String getActor_name() {
        return actor_name;
    }

    public void setActor_name(String actor_name) {
        this.actor_name = actor_name;
    }

    public char getActorFirstLetter(){
        return actor_name.charAt(0);
    }
    public char getActorSecondLetter(){
        return actor_name.charAt(1);
    }

}
