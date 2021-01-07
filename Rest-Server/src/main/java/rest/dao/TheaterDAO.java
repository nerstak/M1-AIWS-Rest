package rest.dao;

import rest.model.Theater;
import rest.utils.Constants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TheaterDAO extends DaoModel implements Dao<Theater> {
    @Override
    public boolean insert(Theater theater) {
        try {
            // Query
            int i = 1;
            PreparedStatement ps = conn.prepareStatement(Constants.RES_THEATER_INSERT);
            ps.setInt(i++, theater.getIdCity());
            ps.setString(i, theater.getName());

            // Result
            ps.execute();
            ResultSet rs = ps.getResultSet();
            if(rs != null && rs.next()) {
                theater.setId(rs.getInt(1));
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Theater theater) {
        try {
            // Query
            PreparedStatement ps = conn.prepareStatement(Constants.RES_THEATER_DELETE);
            ps.setInt(1, theater.getId());

            // Result
            int r = ps.executeUpdate();
            return r > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Theater> selectAll() {
        List<Theater> theaters = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(Constants.RES_THEATERS_SELECT_ALL);
            ResultSet rs = ps.executeQuery();
            while(rs != null && rs.next()) {
                Theater m = extractObj(rs);

                theaters.add(m);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return theaters;
    }

    @Override
    public Theater selectID(int id) {
        try {
            // Query
            PreparedStatement ps = conn.prepareStatement(Constants.RES_THEATER_SELECT_ID);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            // Result
            if(rs != null && rs.next()) {
                return extractObj(rs);
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
    public List<Theater> selectAllFromCity(int idCity) {
        List<Theater> theaters = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(Constants.RES_THEATERS_SELECT_ALL_CITY);
            ps.setInt(1, idCity);
            ResultSet rs = ps.executeQuery();
            while(rs != null && rs.next()) {
                Theater t = extractObj(rs);

                theaters.add(t);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return theaters;
    }

    /**
     * Select all MovieTheaters from a city displaying a movie
     * @param idCity City
     * @param idMovie Movie
     * @return List of MovieTheaters
     */
    public List<Theater> selectAllFromCityMovie(int idCity, int idMovie) {
        List<Theater> theaters = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(Constants.RES_THEATERS_SELECT_MOVIE);
            ps.setInt(1, idCity);
            ps.setInt(2, idMovie);
            ResultSet rs = ps.executeQuery();
            while(rs != null && rs.next()) {
                Theater t = extractObj(rs);

                theaters.add(t);
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
    private Theater extractObj(ResultSet rs) throws SQLException {
        Theater t = new Theater();

        t.setId(rs.getInt("id_theater"));
        t.setName(rs.getString("name_theater"));
        t.setIdCity(rs.getInt("id_city"));

        return t;
    }
}
