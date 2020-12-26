package rest.todo.dao;

import rest.todo.model.Movie;

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

    public List<Movie> selectAll() {
        List<Movie> movies = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM movie");
            ResultSet rs = ps.executeQuery();
            while(rs != null && rs.next()) {
                Movie m = new Movie();
                m.setIdMovie(rs.getInt("id_movie"));
                m.setDirection(rs.getString("director"));
                m.setDuration(rs.getString("duration"));
                m.setMinimumAge(rs.getInt("min_age"));

                movies.add(m);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

        return movies;
    }
}
