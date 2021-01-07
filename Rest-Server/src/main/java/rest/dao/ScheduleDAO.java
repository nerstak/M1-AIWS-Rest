package rest.dao;

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
        try {
            // Query
            PreparedStatement ps = conn.prepareStatement(Constants.RES_SCHEDULE_INSERT);
            ps.setInt(1, schedule.getIdMovie());
            ps.setInt(2, schedule.getIdTheater());
            ps.setTime(3, schedule.getTimeFormatted());
            ps.setInt(4, schedule.getDayOfWeekFormatted());

            // Result
            ps.execute();
            ResultSet rs = ps.getResultSet();
            if(rs != null && rs.next()) {
                schedule.setIdSchedule(rs.getInt(1));
                return true;
            }
        } catch (SQLException|IllegalArgumentException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Schedule schedule) {
        try {
            // Query
            PreparedStatement ps = conn.prepareStatement(Constants.RES_SCHEDULE_DELETE);
            ps.setInt(1, schedule.getId());

            // Result
            int r = ps.executeUpdate();
            return r > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    /**
     * Delete all schedules corresponding to movieDisplaying
     * @param movieDisplaying Movie displaying
     * @return Assertion
     */
    public boolean delete(MovieDisplay movieDisplaying) {
        try {
            // Query
            PreparedStatement ps = conn.prepareStatement(Constants.RES_SCHEDULE_DISPLAY_DELETE_DISPLAY);
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
    public List<Schedule> selectAll() {
        return null;
    }

    @Override
    public Schedule selectID(int id) {
        try {
            // Query
            PreparedStatement ps = conn.prepareStatement(Constants.RES_SCHEDULE_SELECT_ID);
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
            while (rs != null && rs.next()) {
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

        s.setIdSchedule(rs.getInt("id_schedule"));
        s.setIdMovie(rs.getInt("id_movie"));
        s.setIdTheater(rs.getInt("id_theater"));
        s.setDayOfWeek(DayOfWeek.of(rs.getInt("day_of_week")).toString());
        s.setTime(Schedule.getDateFormat().format(rs.getTimestamp("time_day").getTime()));

        return s;
    }
}
