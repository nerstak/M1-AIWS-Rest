package rest.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

public class City implements Serializable {
    @XmlAttribute
    private int idCity;
    private String name;

    @XmlElementWrapper(name = "theaters")
    @XmlElement(name = "theater")
    private List<MovieTheater> theaters = new ArrayList<>();

    public City() {}

    public City(int idCity, String name, List<MovieTheater> theaters) {
        this.idCity = idCity;
        this.name = name;
        this.theaters = theaters;
    }

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MovieTheater> getTheaters() {
        return theaters;
    }

    public void setTheaters(List<MovieTheater> theaters) {
        this.theaters = theaters;
    }
}
