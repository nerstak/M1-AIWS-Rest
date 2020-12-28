package rest.utils;

public class Constants {
    /* Properties */
    public static final String DB_PROPERTIES = "/WEB-INF/db.properties";

    /* Resources */
    // Movies
    public static final String RES_MOVIES_SELECT_ALL = "SELECT * FROM movie";

    // Movie
    public static final String RES_MOVIE_INSERT = "INSERT INTO movie(title, duration, min_age, director) VALUES (?, ?, ?, ?)";
    public static final String RES_MOVIE_SELECT_ID = "SELECT * FROM movie WHERE id_movie = ?";
    public static final String RES_MOVIE_DELETE = "DELETE FROM movie WHERE id_movie = ?";

    // Actor
    public static final String RES_ACTOR_SELECT_NAME = "SELECT * FROM actor WHERE name_actor = ?";
    public static final String RES_ACTOR_SELECT_ID = "SELECT * FROM actor WHERE id_actor = ?";
    public static final String RES_ACTOR_INSERT = "INSERT INTO actor(name_actor) VALUES (?)";
}
