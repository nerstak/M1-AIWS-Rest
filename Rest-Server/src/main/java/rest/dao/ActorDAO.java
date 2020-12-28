package rest.dao;

import rest.model.Actor;
import rest.utils.Constants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ActorDAO extends DaoModel implements Dao<Actor> {
    @Override
    public boolean insert(Actor actor) {
        try {
            // Query
            int i = 1;
            PreparedStatement ps = conn.prepareStatement(Constants.RES_ACTOR_INSERT);
            ps.setString(i, actor.getName());

            // Result
            return ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Actor actor) {
        return false;
    }

    @Override
    public boolean update(Actor actor) {
        return false;
    }

    @Override
    public List<Actor> selectAll() {
        return null;
    }

    @Override
    public Actor selectID(int id) {
        try {
            // Query
            PreparedStatement ps = conn.prepareStatement(Constants.RES_ACTOR_SELECT_ID);
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

    public Actor selectName(String name) {
        try {
            // Query
            PreparedStatement ps = conn.prepareStatement(Constants.RES_ACTOR_SELECT_NAME);
            ps.setString(1, name);

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

    private Actor extractObj(ResultSet rs) throws SQLException {
        Actor actor = new Actor();

        actor.setName(rs.getString("name_actor"));
        actor.setIdActor(rs.getInt("id_actor"));

        return actor;
    }
}
