package rest.utils;

public class Constants {
    /* Properties */
    public static final String DB_PROPERTIES = "/WEB-INF/db.properties";
    public static final int PROP_TOKEN_VALIDITY = 600000;
    public static final String PROP_TOKEN_ISSUER = "JWT_M1_SE2";

    /* Errors */
    public static final String ERROR_CONNECTION_ERROR = "Error during authentication";
    public static final String ERROR_AUTH_REQUIRED = "Authentication is required";
    public static final String ERROR_NOT_FOUND = "The requested resource was not found";
    public static final String ERROR_DELETE = "The requested resource could not be deleted";
    public static final String ERROR_PUT = "The resource could not be created";
    public static final String ERROR_RESOURCE_PROVIDED = "The resource provided is not compliant";

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
    public static final String RES_CITY_SELECT_MOVIE = "SELECT DISTINCT city.id_city, city.name_city FROM city " +
            "    INNER JOIN theater t on city.id_city = t.id_city " +
            "    INNER JOIN movie_display mv ON mv.id_theater = t.id_theater " +
            "    WHERE id_movie = ? AND city.id_city = ?";
    public static final String RES_CITY_SELECT_NAME = "SELECT * FROM city WHERE name_city = ?";
    public static final String RES_CITIES_SELECT_ALL = "SELECT * FROM city";
    public static final String RES_CITIES_SELECT_MOVIE = "SELECT DISTINCT city.id_city, city.name_city FROM city " +
            "    INNER JOIN theater t on city.id_city = t.id_city " +
            "    INNER JOIN movie_display mv ON mv.id_theater = t.id_theater " +
            "    WHERE id_movie = ?";

    // Theater
    public static final String RES_THEATER_INSERT = "INSERT INTO theater(id_city, name_theater) VALUES (?, ?) RETURNING id_theater";
    public static final String RES_THEATER_DELETE = "DELETE FROM theater WHERE id_theater = ?";
    public static final String RES_THEATER_SELECT_ID = "SELECT * FROM theater WHERE id_theater = ?";
    public static final String RES_THEATERS_SELECT_ALL = "SELECT * FROM theater";
    public static final String RES_THEATERS_SELECT_ALL_CITY = "SELECT * FROM theater WHERE id_city = ?";
    public static final String RES_THEATERS_SELECT_MOVIE = "SELECT theater.id_city, theater.id_theater, theater.name_theater FROM theater " +
            "    INNER JOIN movie_display md ON theater.id_theater = md.id_theater " +
            "    WHERE id_city = ? AND id_movie = ?";

    // Manager
    public static final String RES_MANAGER_INSERT = "INSERT INTO manager(id_theater, username, password) VALUES (?, ?, ?) RETURNING id_manager";
    public static final String RES_MANAGER_SELECT_ID = "SELECT * FROM manager WHERE id_manager = ?";
    public static final String RES_MANAGER_SELECT_USERNAME = "SELECT * FROM manager WHERE username = ?";

    // Movie displaying
    public static final String RES_MOVIE_DISPLAY_SELECT_ID = "SELECT * FROM movie_display WHERE id_movie = ? AND id_theater = ?";

    // Schedules
    public static final String RES_SCHEDULES_SELECT_ID = "SELECT * FROM schedule WHERE id_movie = ? AND id_theater = ?";

}
