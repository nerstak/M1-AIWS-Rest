package rest.dao;

import rest.model.Manager;
import rest.utils.Constants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ManagerDAO extends DaoModel implements Dao<Manager>{
    @Override
    public boolean insert(Manager manager) {
        try {
            // Query
            int i = 1;
            PreparedStatement ps = conn.prepareStatement(Constants.RES_MANAGER_INSERT);
            ps.setInt(i++, manager.getIdTheater());
            ps.setString(i++, manager.getUsername());
            ps.setString(i, manager.getPassword());

            // Result
            ps.execute();
            ResultSet rs = ps.getResultSet();
            if(rs != null && rs.next()) {
                manager.setIdManager(rs.getInt(1));
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Manager manager) {
        return false;
    }

    @Override
    public List<Manager> selectAll() {
        return null;
    }

    @Override
    public Manager selectID(int id) {
        try {
            // Query
            PreparedStatement ps = conn.prepareStatement(Constants.RES_MANAGER_SELECT_ID);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            // Result
            if(rs != null && rs.next()) {
                return extractObj(rs);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

        return null;
    }

    /**
     * Select Manager by username
     * @param username Username
     * @return Manager (may be null)
     */
    public Manager selectUsername(String username) {
        try {
            // Query
            PreparedStatement ps = conn.prepareStatement(Constants.RES_MANAGER_SELECT_USERNAME);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            // Result
            if(rs != null && rs.next()) {
                return extractObj(rs);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

        return null;
    }

    /**
     * Extract a Manager from a ResultSet
     * @param rs ResultSet
     * @return Manager
     * @throws SQLException Exception
     */
    private Manager extractObj(ResultSet rs) throws SQLException {
        Manager m = new Manager();

        m.setIdManager(rs.getInt("id_manager"));
        m.setIdTheater(rs.getInt("id_theater"));
        m.setUsername(rs.getString("username"));
        m.setPassword(rs.getString("password"));

        return m;
    }
}
