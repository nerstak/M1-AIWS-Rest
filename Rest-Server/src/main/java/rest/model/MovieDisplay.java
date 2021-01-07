package rest.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


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

    public Date getStartDateFormatted() throws ParseException {
        return dateFormat.parse(startDate);
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public Date getEndDateFormatted() throws ParseException {
        return dateFormat.parse(endDate);
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public static DateFormat getDateFormat() {
        return dateFormat;
    }
}
