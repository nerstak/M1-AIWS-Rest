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
    private List<Theater> theaters = new ArrayList<>();

    public City() {}

    public City(int idCity, String name, List<Theater> theaters) {
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

    public List<Theater> getTheaters() {
        return theaters;
    }

    public void setTheaters(List<Theater> theaters) {
        this.theaters = theaters;
    }
}
