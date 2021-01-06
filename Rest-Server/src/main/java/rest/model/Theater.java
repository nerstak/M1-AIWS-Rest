package rest.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "theater")
@XmlAccessorType(XmlAccessType.FIELD)
public class Theater implements Serializable  {
    @XmlAttribute
    private int id;
    private String name;
    @XmlAttribute
    private int idCity;

    @XmlElementWrapper(name = "schedules")
    @XmlElement(name = "schedule")
    private List<Schedule> schedules = new ArrayList<>();

    public Theater() {}

    public Theater(int id, String name, int idCity, List<Schedule> schedules) {
        this.id = id;
        this.name = name;
        this.idCity = idCity;
        this.schedules = schedules;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}
