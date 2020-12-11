package rest.todo.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MovieTheater {
    private int idMovieTheater;
    private String name;
    private int idCity;
    // Todo : convert to a hashed password
    private String password;

    public MovieTheater() {}

    public MovieTheater(int idMovieTheater, String name, int idCity, String password) {
        this.idMovieTheater = idMovieTheater;
        this.name = name;
        this.idCity = idCity;
        this.password = password;
    }

    public int getIdMovieTheater() {
        return idMovieTheater;
    }

    public void setIdMovieTheater(int idMovieTheater) {
        this.idMovieTheater = idMovieTheater;
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
