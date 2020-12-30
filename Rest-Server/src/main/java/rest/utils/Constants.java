package rest.utils;

public class Constants {
    /* Properties */
    public static final String DB_PROPERTIES = "/WEB-INF/db.properties";
    public static final int PROP_TOKEN_VALIDITY = 600000;
    public static final String PROP_TOKEN_ISSUER = "JWT_M1_SE2";

    /* Errors */
    public static final String ERROR_CONNECTION_ERROR = "Error during authentication";

    /* Resources */
    // Movie
    public static final String RES_MOVIE_INSERT = "INSERT INTO movie(title, duration, min_age, director) VALUES (?, ?, ?, ?) RETURNING id_movie";
    public static final String RES_MOVIE_SELECT_ID = "SELECT * FROM movie WHERE id_movie = ?";
    public static final String RES_MOVIE_DELETE = "DELETE FROM movie WHERE id_movie = ?";
    public static final String RES_MOVIES_SELECT_ALL = "SELECT * FROM movie";


    // Actor
    public static final String RES_ACTOR_SELECT_NAME = "SELECT * FROM actor WHERE name_actor = ?";
    public static final String RES_ACTOR_SELECT_ID = "SELECT * FROM actor WHERE id_actor = ?";
    public static final String RES_ACTOR_INSERT = "INSERT INTO actor(name_actor) VALUES (?) RETURNING id_actor";

    // Actor playing
    public static final String RES_ACTOR_PLAYING_SELECT_ID = "SELECT * FROM actors_playing WHERE id_actor = ? AND id_movie = ?";
    public static final String RES_ACTOR_PLAYING_SELECT_ALL = "SELECT a.id_actor, a.name_actor " +
                                                                "FROM actor AS a " +
                                                                "    inner join actors_playing AS p " +
                                                                "        ON p.id_actor = a.id_actor " +
                                                                "        WHERE p.id_movie = ?";
    public static final String RES_ACTOR_PLAYING_INSERT = "INSERT INTO actors_playing(id_actor, id_movie) VALUES (?,?)";

    // City
    public static final String RES_CITY_INSERT = "INSERT INTO city(name_city) VALUES (?) RETURNING id_city";
    public static final String RES_CITY_SELECT_ID = "SELECT * FROM city WHERE id_city = ?";
    public static final String RES_CITY_DELETE = "DELETE FROM city WHERE id_city = ?";
    public static final String RES_CITY_SELECT_NAME = "SELECT * FROM city WHERE name_city = ?";
    public static final String RES_CITIES_SELECT_ALL = "SELECT * FROM city";

    // Movie Theater
    public static final String RES_THEATER_SELECT_ID = "SELECT * FROM theater WHERE id_theater = ?";
    public static final String RES_THEATERS_SELECT_ALL = "SELECT * FROM theater";
    public static final String RES_THEATERS_SELECT_ALL_CITY = "SELECT * FROM theater WHERE id_city = ?";

    // Manager
    public static final String RES_MANAGER_SELECT_ID = "SELECT * FROM manager WHERE id_manager = ?";
    public static final String RES_MANAGER_SELECT_USERNAME = "SELECT * FROM manager WHERE username = ?";
}
