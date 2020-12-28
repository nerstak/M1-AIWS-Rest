package rest.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Movie implements Serializable {
    @XmlAttribute
    private int idMovie;
    private String duration;
    private String direction;
    private int minimumAge;
    private String title;

    @XmlElementWrapper(name = "actors")
    @XmlElement(name = "actor")
    private List<Actor> actors = new ArrayList<>();

    public Movie() {}

    public Movie(int idMovie, String duration, String direction, int minimumAge, String title, ArrayList<Actor> actors) {
        this.idMovie = idMovie;
        this.duration = duration;
        this.direction = direction;
        this.minimumAge = minimumAge;
        this.title = title;
        this.actors = actors;
    }

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getMinimumAge() {
        return minimumAge;
    }

    public void setMinimumAge(int minimumAge) {
        if(minimumAge >= 0) {
            this.minimumAge = minimumAge;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
}
