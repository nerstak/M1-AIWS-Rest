package rest.todo.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.DayOfWeek;
import java.time.LocalTime;

@XmlRootElement
public class Schedule {
    private int idMovie;
    private int idTheater;
    private LocalTime time;
    private DayOfWeek dayOfWeek;

    public Schedule(int idMovie) {}

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

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
