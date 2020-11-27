package fileio;

public final class UserRating {
    private String username;
    private int noofratings;
    public UserRating(final String username, final int noofratings) {
        this.username = username;
        this.noofratings = noofratings;
    }
    public char getFirstLetter() {
        return username.charAt(0);
    }

    public char getSecondLetter() {
        return  username.charAt(1);
    }

    public String getUsername()  {
        return username;
    }

    @Override
    public String toString() {
        return "UserRating{"
                + "user_name='"
                + username
                + '\''
                + ", no_of_ratings="
                + noofratings
                + '}';
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public int getNoofratings() {
        return noofratings;
    }

    public void setNoofratings(final int noofratings) {
        this.noofratings = noofratings;
    }
}
