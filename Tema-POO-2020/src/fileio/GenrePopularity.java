package fileio;

import entertainment.Genre;

public final class GenrePopularity {
    private Genre genre;
    private int popularity;
    public GenrePopularity(final Genre genre, final int popularity) {
        this.genre = genre;
        this.popularity = popularity;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(final Genre genre) {
        this.genre = genre;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(final int popularity) {
        this.popularity = popularity;
    }
}
