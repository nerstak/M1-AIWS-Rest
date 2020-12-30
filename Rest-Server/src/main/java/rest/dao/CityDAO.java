package rest.dao;

import rest.model.City;
import rest.utils.Constants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CityDAO extends DaoModel implements Dao<City> {
    @Override
    public boolean insert(City city) {
        try {
            // Query
            PreparedStatement ps = conn.prepareStatement(Constants.RES_CITY_INSERT);
            ps.setString(1, city.getName());

            // Result
            ps.execute();
            ResultSet rs = ps.getResultSet();
            if(rs != null && rs.next()) {
                city.setIdCity(rs.getInt(1));
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(City city) {
        if(new MovieTheaterDAO().selectAllFromCity(city.getIdCity()).size() == 0) {
            try {
                // Query
                PreparedStatement ps = conn.prepareStatement(Constants.RES_CITY_DELETE);
                ps.setInt(1, city.getIdCity());

                // Result
                int r = ps.executeUpdate();
                return r > 0;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean update(City city) {
        return false;
    }

    @Override
    public List<City> selectAll() {
        List<City> cities = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(Constants.RES_CITIES_SELECT_ALL);
            ResultSet rs = ps.executeQuery();
            while(rs != null && rs.next()) {
                City c = extractObj(rs);
                c.setTheaters(new MovieTheaterDAO().selectAllFromCity(c.getIdCity()));

                cities.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return cities;
    }

    @Override
    public City selectID(int id) {
        try {
            // Query
            PreparedStatement ps = conn.prepareStatement(Constants.RES_CITY_SELECT_ID);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            // Result
            if(rs != null && rs.next()) {
                City c = extractObj(rs);
                c.setTheaters(new MovieTheaterDAO().selectAllFromCity(c.getIdCity()));
                return c;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

        return null;
    }

    /**
     * Extract City from ResultSet
     * @param rs ResultSet
     * @return City
     * @throws SQLException Exception
     */
    private City extractObj(ResultSet rs) throws SQLException {
        City c = new City();

        c.setIdCity(rs.getInt("id_city"));
        c.setName(rs.getString("name_city"));

        return c;
    }
}
