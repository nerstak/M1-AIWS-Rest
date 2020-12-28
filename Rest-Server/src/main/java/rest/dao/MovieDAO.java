package rest.dao;

import rest.model.Actor;
import rest.model.Movie;
import rest.utils.Constants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO dedicated to movie handling
 */
public class MovieDAO extends DaoModel implements Dao<Movie> {
    public MovieDAO() {
        super();
    }

    @Override
    public boolean insert(Movie movie) {
        try {
            // Query
            int i = 1;
            PreparedStatement ps = conn.prepareStatement(Constants.RES_MOVIE_INSERT);
            ps.setString(i++, movie.getTitle());
            ps.setString(i++, movie.getDuration());
            ps.setInt(i++, movie.getMinimumAge());
            ps.setString(i, movie.getDirection());

            // Result
            ps.execute();
            ResultSet rs = ps.getResultSet();
            if(rs != null && rs.next()) {
                movie.setIdMovie(rs.getInt(1));
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Movie movie) {
        try {
            // Query
            PreparedStatement ps = conn.prepareStatement(Constants.RES_MOVIE_DELETE);
            ps.setInt(1, movie.getIdMovie());

            // Result
            int r = ps.executeUpdate();
            return r > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Movie movie) {
        return false;
    }

    @Override
    public Movie selectID(int id) {
        try {
            // Query
            PreparedStatement ps = conn.prepareStatement(Constants.RES_MOVIE_SELECT_ID);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            // Result
            if(rs != null && rs.next()) {
                Movie m = extractObj(rs);
                m.setActors(new ActorDAO().selectAllActorInMovie(m));
                return m;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

        return null;
    }

    /**
     * Create a movie from a row in the database
     * @param rs ResultSet
     * @return Movie created
     * @throws SQLException Error with ResultSet
     */
    private Movie extractObj(ResultSet rs) throws SQLException {
        Movie m = new Movie();

        m.setIdMovie(rs.getInt("id_movie"));
        m.setDirection(rs.getString("director"));
        m.setDuration(rs.getString("duration"));
        m.setMinimumAge(rs.getInt("min_age"));
        m.setTitle(rs.getString("title"));

        return m;
    }

    @Override
    public List<Movie> selectAll() {
        List<Movie> movies = new ArrayList<>();
        ActorDAO actorDAO = new ActorDAO();

        try {
            PreparedStatement ps = conn.prepareStatement(Constants.RES_MOVIES_SELECT_ALL);
            ResultSet rs = ps.executeQuery();
            while(rs != null && rs.next()) {
                Movie m = extractObj(rs);
                m.setActors(actorDAO.selectAllActorInMovie(m));

                movies.add(m);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return movies;
    }

    /**
     * Add an actor to a movie
     * @param movie Movie
     * @param actor Actor
     * @return Assertion
     */
    public boolean addActorToMovie(Movie movie, Actor actor) {
        if(!checkActorInMovie(movie, actor)) {
            return insertActorInMovie(movie, actor);
        }
        return false;
        // Should insert in actors playing
    }

    /**
     * Check if an actor is in a movie
     * @param movie Movie
     * @param actor Actor
     * @return If actor is in movie
     */
    private boolean checkActorInMovie(Movie movie, Actor actor) {
        try {
            PreparedStatement ps = conn.prepareStatement(Constants.RES_ACTOR_PLAYING_SELECT_ID);
            ps.setInt(1, actor.getIdActor());
            ps.setInt(2,movie.getIdMovie());

            ResultSet rs = ps.executeQuery();
            if(rs != null && rs.next()) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    /**
     * Insert actor in movie
     * @param movie Movie
     * @param actor Actor
     * @return Assertion
     */
    private boolean insertActorInMovie(Movie movie, Actor actor) {
        try {
            PreparedStatement ps = conn.prepareStatement(Constants.RES_ACTOR_PLAYING_INSERT);
            ps.setInt(1, actor.getIdActor());
            ps.setInt(2,movie.getIdMovie());

            return ps.executeUpdate() == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

}
