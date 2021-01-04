package rest.dao;

import rest.model.City;
import rest.model.MovieDisplay;
import rest.model.Schedule;
import rest.utils.Constants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO extends DaoModel implements Dao<Schedule> {
    @Override
    public boolean insert(Schedule schedule) {
        return false;
    }

    @Override
    public boolean delete(Schedule schedule) {
        return false;
    }

    @Override
    public boolean update(Schedule schedule) {
        return false;
    }

    @Override
    public List<Schedule> selectAll() {
        return null;
    }

    @Override
    public Schedule selectID(int id) {
        return null;
    }

    /**
     * Select all schedules of Movie in a Theater
     * @param idMovie id of Movie
     * @param idTheater id of Theater
     * @return List of Schedules
     */
    public List<Schedule> selectAll(int idMovie, int idTheater) {
        List<Schedule> schedules = new ArrayList<>();

        try {
            // Query
            PreparedStatement ps = conn.prepareStatement(Constants.RES_SCHEDULES_SELECT_ID);
            ps.setInt(1, idMovie);
            ps.setInt(2, idTheater);

            ResultSet rs = ps.executeQuery();

            // Result
            if(rs != null && rs.next()) {
                Schedule s = extractObj(rs);
                schedules.add(s);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

        return schedules;
    }

    /**
     * Extract Schedule from ResultSet
     * @param rs ResultSet
     * @return Schedule
     * @throws SQLException Exception
     */
    private Schedule extractObj(ResultSet rs) throws SQLException {
        Schedule s = new Schedule();

        s.setIdMovie(rs.getInt("id_movie"));
        s.setIdTheater(rs.getInt("id_theater"));
        s.setDayOfWeek(DayOfWeek.of(rs.getInt("day_of_week")).toString());
        s.setTime(Schedule.getDateFormat().format(rs.getTimestamp("time_day").getTime()));

        return s;
    }
}
