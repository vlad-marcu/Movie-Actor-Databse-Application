package fileio;

public final class ShowRecommandationFavorite {
    private String showtitle;
    private int favoriteappereances = 0;
    private int index;
    public ShowRecommandationFavorite(final String showtitle,
                                      final int favoriteappereances,
                                      final int index) {
        this.showtitle = showtitle;
        this.favoriteappereances = favoriteappereances;
        this.index = index;
    }

    public String getShowtitle() {
        return showtitle;
    }

    @Override
    public String toString() {
        return "ShowRecommandationFavorite{"
                + "show_title='"
                + showtitle
                + '\''
                + ", favorite_appereances="
                + favoriteappereances
                + ", index="
                + index
                + '}';
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(final int index) {
        this.index = index;
    }

    public void setShowtitle(final String showtitle) {
        this.showtitle = showtitle;
    }

    public int getFavoriteappereances() {
        return favoriteappereances;
    }

    public void setFavoriteappereances(final int favoriteappereances) {
        this.favoriteappereances = favoriteappereances;
    }
}
