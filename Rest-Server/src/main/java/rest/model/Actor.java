package rest.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlRootElement (name = "actor")
@XmlAccessorType(XmlAccessType.FIELD)
public class Actor implements Serializable {
    @XmlValue
    private String name;

    @XmlAttribute(name = "id")
    private int idActor;

    public Actor() {
    }

    public Actor(String name, int idActor) {
        this.name = name;
        this.idActor = idActor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdActor() {
        return idActor;
    }

    public void setIdActor(int idActor) {
        this.idActor = idActor;
    }
}
