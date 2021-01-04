package rest.dao;

import rest.model.MovieDisplay;
import rest.utils.Constants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MovieDisplayDAO extends DaoModel implements Dao<MovieDisplay> {
    @Override
    public boolean insert(MovieDisplay movieDisplaying) {
        return false;
    }

    @Override
    public boolean delete(MovieDisplay movieDisplaying) {
        return false;
    }

    @Override
    public boolean update(MovieDisplay movieDisplaying) {
        return false;
    }

    @Override
    public List<MovieDisplay> selectAll() {
        return null;
    }

    @Override
    public MovieDisplay selectID(int id) {
        return null;
    }

    /**
     * Select MovieDisplay for Movie in Theater
     * @param idMovie id of Movie
     * @param idTheater id of Theater
     * @return MovieDisplay
     */
    public MovieDisplay selectID(int idMovie, int idTheater) {
        try {
            // Query
            PreparedStatement ps = conn.prepareStatement(Constants.RES_MOVIE_DISPLAY_SELECT_ID);
            ps.setInt(1, idMovie);
            ps.setInt(2, idTheater);

            ResultSet rs = ps.executeQuery();

            // Result
            if(rs != null && rs.next()) {
                MovieDisplay md = extractObj(rs);
                md.setSchedules(new ScheduleDAO().selectAll(idMovie,idTheater));
                return md;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

        return null;
    }

    /**
     * Extract MovieDisplay from ResultSet
     * @param rs ResultSet
     * @return MovieDisplay
     * @throws SQLException Exception
     */
    private MovieDisplay extractObj(ResultSet rs) throws SQLException {
        MovieDisplay md = new MovieDisplay();

        md.setIdMovie(rs.getInt("id_movie"));
        md.setIdTheater(rs.getInt("id_theater"));
        md.setLanguage(rs.getString("lang"));
        md.setStartDate(MovieDisplay.getDateFormat().format(rs.getTimestamp("start_date")));
        md.setEndDate(MovieDisplay.getDateFormat().format(rs.getTimestamp("end_date")));

        return md;
    }
}
