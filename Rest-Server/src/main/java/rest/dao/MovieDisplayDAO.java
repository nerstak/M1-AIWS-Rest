package rest.dao;

import rest.model.MovieDisplay;
import rest.utils.Constants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class MovieDisplayDAO extends DaoModel implements Dao<MovieDisplay> {
    @Override
    public boolean insert(MovieDisplay movieDisplay) {
        try {
            // Query
            int i = 1;
            PreparedStatement ps = conn.prepareStatement(Constants.RES_MOVIE_DISPLAY_INSERT);
            ps.setInt(i++, movieDisplay.getIdMovie());
            ps.setInt(i++, movieDisplay.getIdTheater());
            ps.setString(i++, movieDisplay.getLanguage());
            ps.setDate(i++, new java.sql.Date(movieDisplay.getStartDateFormatted().getTime()));
            ps.setDate(i, new java.sql.Date(movieDisplay.getEndDateFormatted().getTime()));


            ps.execute();

            return true;
        } catch (SQLException| ParseException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(MovieDisplay movieDisplaying) {
        try {
            // Query
            PreparedStatement ps = conn.prepareStatement(Constants.RES_MOVIE_DISPLAY_DELETE);
            ps.setInt(1, movieDisplaying.getIdMovie());
            ps.setInt(2, movieDisplaying.getIdTheater());

            // Result
            int r = ps.executeUpdate();
            return r > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
                return extractObj(rs);
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
