package rest.resources;

import rest.dao.MovieTheaterDAO;
import rest.model.MovieTheater;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.util.List;

import static java.lang.Integer.parseInt;

public class MovieTheatersResource {
    @Context
    private UriInfo uriInfo;
    @Context
    private Request request;

    private int idCity;


    private MovieTheaterDAO movieTheaterDAO = new MovieTheaterDAO();

    public MovieTheatersResource() {
    }

    public MovieTheatersResource(UriInfo uriInfo, Request request, int idCity) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.idCity = idCity;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<MovieTheater> getMovies() {
        return movieTheaterDAO.selectAllFromCity(idCity);
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCount() {
        int count = movieTheaterDAO.selectAllFromCity(idCity).size();
        return String.valueOf(count);
    }

    @Path("{movieTheater}")
    public MovieTheaterResource getMovieTheater(@PathParam("movieTheater") String id) {
        return new MovieTheaterResource(uriInfo, request, idCity, parseInt(id));
    }
}
