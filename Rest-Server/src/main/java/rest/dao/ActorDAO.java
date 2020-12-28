package rest.dao;

import rest.model.Actor;
import rest.model.Movie;
import rest.utils.Constants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActorDAO extends DaoModel implements Dao<Actor> {
    @Override
    public boolean insert(Actor actor) {
        // Checking if actor is not already inserted
        Actor tmp = selectName(actor.getName());
        if(tmp != null) {
            actor.setIdActor(tmp.getIdActor());
            return false;
        }

        try {
            // Query
            int i = 1;
            PreparedStatement ps = conn.prepareStatement(Constants.RES_ACTOR_INSERT);
            ps.setString(i, actor.getName());

            // Result
            ps.execute();
            ResultSet rs = ps.getResultSet();
            if(rs != null && rs.next()) {
                actor.setIdActor(rs.getInt(1));
                return true;
            }
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

    /**
     * Select all actors in a movie
     * @param movie Movie
     * @return List of actors
     */
    public List<Actor> selectAllActorInMovie(Movie movie) {
        List<Actor> actors = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(Constants.RES_ACTOR_PLAYING_SELECT_ALL);
            ps.setInt(1,movie.getIdMovie());
            ResultSet rs = ps.executeQuery();
            while(rs != null && rs.next()) {
                Actor a = extractObj(rs);

                actors.add(a);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return actors;
    }
}
