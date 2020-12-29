package rest.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "theater")
@XmlAccessorType(XmlAccessType.FIELD)
public class MovieTheater implements Serializable {
    @XmlAttribute
    private int id;
    private String name;
    @XmlAttribute
    private int idCity;
    // Todo : convert to a hashed password
    private String password;

    public MovieTheater() {}

    public MovieTheater(int id, String name, int idCity, String password) {
        this.id = id;
        this.name = name;
        this.idCity = idCity;
        this.password = password;
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

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
