package rest.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class MovieDisplay {
    private int idMovie;
    private int idTheater;
    private String language;
    // Todo: Check if type is relevant for actions
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private List<Schedule> schedules = new ArrayList<>();

    public MovieDisplay() {}

    public MovieDisplay(int idMovie, int idTheater, String language, LocalDateTime startDate, LocalDateTime endDate) {
        this.idMovie = idMovie;
        this.idTheater = idTheater;
        this.language = language;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public int getIdTheater() {
        return idTheater;
    }

    public void setIdTheater(int idTheater) {
        this.idTheater = idTheater;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        if(startDate.isBefore(this.endDate)) {
            this.startDate = startDate;
        }
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        if(endDate.isAfter(this.startDate)) {
            this.endDate = endDate;
        }
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}
