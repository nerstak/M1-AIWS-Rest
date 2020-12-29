package rest.dao;

import rest.model.Movie;
import rest.model.MovieTheater;
import rest.utils.Constants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MovieTheaterDAO extends DaoModel implements Dao<MovieTheater> {
    @Override
    public boolean insert(MovieTheater movieTheater) {
        return false;
    }

    @Override
    public boolean delete(MovieTheater movieTheater) {
        return false;
    }

    @Override
    public boolean update(MovieTheater movieTheater) {
        return false;
    }

    @Override
    public List<MovieTheater> selectAll() {
        return null;
    }

    @Override
    public MovieTheater selectID(int id) {
        try {
            // Query
            PreparedStatement ps = conn.prepareStatement(Constants.RES_MOVIE_SELECT_ID);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            // Result
            if(rs != null && rs.next()) {
                MovieTheater mt = extractObj(rs);
                return mt;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private MovieTheater extractObj(ResultSet rs) throws SQLException {
        MovieTheater mt = new MovieTheater();

        mt.setId(rs.getInt("id_theater"));
        mt.setName(rs.getString("name_theater"));
        mt.setPassword(rs.getString("password"));

        return mt;
    }
}
