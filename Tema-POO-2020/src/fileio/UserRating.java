package fileio;

public class UserRating {
    private String user_name;
    private int no_of_ratings;
    public UserRating(String user_name,int no_of_ratings){
        this.user_name=user_name;
        this.no_of_ratings=no_of_ratings;
    }
    public char getFirstLetter(){
        return user_name.charAt(0);
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getNo_of_ratings() {
        return no_of_ratings;
    }

    public void setNo_of_ratings(int no_of_ratings) {
        this.no_of_ratings = no_of_ratings;
    }
}
