package rest.resources;

import rest.dao.MovieDAO;
import rest.model.Movie;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/movies")
public class MoviesResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    private static MovieDAO movieDAO = new MovieDAO();

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Movie> getMovies() {
        return movieDAO.selectAll();
    }
}
