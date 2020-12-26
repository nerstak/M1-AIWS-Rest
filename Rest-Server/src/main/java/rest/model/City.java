package rest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class City {
    private int idCity;
    private String name;

    public City() {}

    public City(int idCity, String name) {
        this.idCity = idCity;
        this.name = name;
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
}
