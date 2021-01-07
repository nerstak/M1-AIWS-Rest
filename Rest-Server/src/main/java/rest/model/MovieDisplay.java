package rest.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "display")
@XmlAccessorType(XmlAccessType.FIELD)
public class MovieDisplay implements Serializable {
    @XmlAttribute
    private int idMovie;
    @XmlAttribute
    private int idTheater;
    private String language;

    private String startDate;
    private String endDate;

    @XmlTransient
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    public MovieDisplay() {}

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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public static DateFormat getDateFormat() {
        return dateFormat;
    }
}
