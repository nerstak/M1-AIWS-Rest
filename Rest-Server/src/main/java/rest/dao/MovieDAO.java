package rest.dao;

import rest.model.Movie;
import rest.utils.Constants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO extends DaoModel implements Dao<Movie> {
    public MovieDAO() {
        super();
    }

    @Override
    public void insert(Movie movie) {

    }

    @Override
    public void delete(Movie movie) {

    }

    @Override
    public void update(Movie movie) {

    }

    public Movie selectID(int id) {
        try {
            PreparedStatement ps = conn.prepareStatement(Constants.RES_MOVIE_SELECT_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs != null && rs.next()) {
                return extractMovie(rs);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

        return null;
    }

    private Movie extractMovie(ResultSet rs) throws SQLException {
        Movie m = new Movie();

        m.setIdMovie(rs.getInt("id_movie"));
        m.setDirection(rs.getString("director"));
        m.setDuration(rs.getString("duration"));
        m.setMinimumAge(rs.getInt("min_age"));
        m.setTitle(rs.getString("title"));

        return m;
    }

    public List<Movie> selectAll() {
        List<Movie> movies = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(Constants.RES_MOVIES_SELECT_ALL);
            ResultSet rs = ps.executeQuery();
            while(rs != null && rs.next()) {
                Movie m = extractMovie(rs);

                movies.add(m);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

        return movies;
    }
}
