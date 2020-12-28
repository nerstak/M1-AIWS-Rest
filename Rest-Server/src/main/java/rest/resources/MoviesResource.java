package rest.resources;

import rest.dao.MovieDAO;
import rest.model.Movie;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.util.List;

/**
 * Movies handling
 */
@Path("/movies")
public class MoviesResource {
    @Context
    private UriInfo uriInfo;
    @Context
    private Request request;

    private static final MovieDAO movieDAO = new MovieDAO();

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Movie> getMovies() {
        return movieDAO.selectAll();
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCount() {
        int count = movieDAO.selectAll().size();
        return String.valueOf(count);
    }

    @Path("{movie}")
    public MovieResource getMovie(@PathParam("movie") String id) {
        return new MovieResource(uriInfo, request, id);
    }
}
