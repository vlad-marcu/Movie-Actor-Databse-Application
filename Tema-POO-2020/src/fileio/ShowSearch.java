package fileio;

public final class ShowSearch {
    private String showtitle;
    private double rating;
    public char getFirstLetter() {
        return showtitle.charAt(0);
    }
    public ShowSearch(final String showtitle, final double rating) {
        this.showtitle = showtitle;
        this.rating = rating;
    }

    public String getShowtitle() {
        return showtitle;
    }

    public void setShowtitle(final String showtitle) {
        this.showtitle = showtitle;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(final double rating) {
        this.rating = rating;
    }
}
