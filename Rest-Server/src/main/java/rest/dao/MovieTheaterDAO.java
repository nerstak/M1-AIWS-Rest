package rest.dao;

import rest.model.MovieTheater;
import rest.utils.Constants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieTheaterDAO extends DaoModel implements Dao<MovieTheater> {
    @Override
    public boolean insert(MovieTheater movieTheater) {
        try {
            // Query
            int i = 1;
            PreparedStatement ps = conn.prepareStatement(Constants.RES_THEATER_INSERT);
            ps.setInt(i++, movieTheater.getIdCity());
            ps.setString(i, movieTheater.getName());

            // Result
            ps.execute();
            ResultSet rs = ps.getResultSet();
            if(rs != null && rs.next()) {
                movieTheater.setIdCity(rs.getInt(1));
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(MovieTheater movieTheater) {
        try {
            // Query
            PreparedStatement ps = conn.prepareStatement(Constants.RES_THEATER_DELETE);
            ps.setInt(1, movieTheater.getId());

            // Result
            int r = ps.executeUpdate();
            return r > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(MovieTheater movieTheater) {
        return false;
    }

    @Override
    public List<MovieTheater> selectAll() {
        List<MovieTheater> theaters = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(Constants.RES_THEATERS_SELECT_ALL);
            ResultSet rs = ps.executeQuery();
            while(rs != null && rs.next()) {
                MovieTheater m = extractObj(rs);

                theaters.add(m);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return theaters;
    }

    @Override
    public MovieTheater selectID(int id) {
        try {
            // Query
            PreparedStatement ps = conn.prepareStatement(Constants.RES_THEATER_SELECT_ID);
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

    /**
     * Select all MovieTheaters from a city
     * @param idCity City
     * @return List of MovieTheaters
     */
    public List<MovieTheater> selectAllFromCity(int idCity) {
        List<MovieTheater> theaters = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(Constants.RES_THEATERS_SELECT_ALL_CITY);
            ps.setInt(1, idCity);
            ResultSet rs = ps.executeQuery();
            while(rs != null && rs.next()) {
                MovieTheater m = extractObj(rs);

                theaters.add(m);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return theaters;
    }

    /**
     * Extract a MovieTheater from a ResultSet
     * @param rs ResultSet
     * @return MovieTheater
     * @throws SQLException Exception
     */
    private MovieTheater extractObj(ResultSet rs) throws SQLException {
        MovieTheater mt = new MovieTheater();

        mt.setId(rs.getInt("id_theater"));
        mt.setName(rs.getString("name_theater"));
        mt.setIdCity(rs.getInt("id_city"));

        return mt;
    }
}
