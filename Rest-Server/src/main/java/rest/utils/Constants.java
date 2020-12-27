package rest.utils;

public class Constants {
    /* Properties */
    public static final String DB_PROPERTIES = "/WEB-INF/db.properties";

    /* Resources */
    // Movies
    public static final String RES_MOVIES_SELECT_ALL = "SELECT * FROM movie";

    // Movie
    public static final String RES_MOVIE_SELECT_ID = "SELECT * FROM movie WHERE id_movie = ?";
    public static final String RES_MOVIE_DELETE = "DELETE FROM movie WHERE id_movie = ?";
}
