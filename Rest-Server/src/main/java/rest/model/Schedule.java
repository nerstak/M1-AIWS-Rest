package rest.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@XmlRootElement(name = "schedule")
@XmlAccessorType(XmlAccessType.FIELD)
public class Schedule implements Serializable {
    @XmlAttribute
    private int idSchedule;
    private String time;
    private String dayOfWeek;

    @XmlTransient
    private int idMovie;
    @XmlTransient
    private int idTheater;



    private static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public Schedule() {
    }

    public int getIdSchedule() {
        return idSchedule;
    }

    public void setIdSchedule(int idSchedule) {
        this.idSchedule = idSchedule;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public static DateFormat getDateFormat() {
        return dateFormat;
    }
}
