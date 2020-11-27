package fileio;

public final class Series {
    private double rating;

    private int noseason;

    public double getRating() {
        return rating;
    }

    public void setRating(final double rating) {
        this.rating = rating;
    }

    public int getNoseason() {
        return noseason;
    }

    public void setNoseason(final int noseason) {
        this.noseason = noseason;
    }



    public Series(final double rating, final int noseason) {
        this.rating = rating;
        this.noseason = noseason;
    }



}
