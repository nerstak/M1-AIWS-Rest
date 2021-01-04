package rest.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@XmlRootElement(name = "schedule")
@XmlAccessorType(XmlAccessType.FIELD)
public class Schedule implements Serializable {
    @XmlTransient
    private int idMovie;
    @XmlTransient
    private int idTheater;

    private String time;
    private String dayOfWeek;

    private static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public Schedule() {
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
