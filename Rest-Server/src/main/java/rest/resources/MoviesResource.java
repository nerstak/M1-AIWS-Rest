package rest.resources;

import rest.dao.ActorDAO;
import rest.dao.MovieDAO;
import rest.model.Actor;
import rest.model.Movie;
import rest.resources.filter.Secured;
import rest.utils.WebException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

import static rest.utils.Constants.ERROR_NOT_FOUND;
import static rest.utils.Constants.ERROR_POST;

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
    private static final ActorDAO actorDAO = new ActorDAO();

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
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
    public MovieResource getMovie(@PathParam("movie") String idString) {
        try {
            int id = Integer.parseInt(idString);
            if(movieDAO.selectID(id) != null) {
                return new MovieResource(uriInfo, request, id);
            }
        } catch (NumberFormatException ignored) { }
        throw new WebException(Response.Status.NOT_FOUND, ERROR_NOT_FOUND);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Secured
    public Response postMovie(Movie movie) {
        Response res;
        res = Response.noContent().build();

        if(movieDAO.insert(movie)) {
            for (Actor a: movie.getActors()) {
                actorDAO.insert(a);
                movieDAO.addActorToMovie(movie, a);
            }
        } else {
            throw new WebException(Response.Status.INTERNAL_SERVER_ERROR, ERROR_POST);
        }
        return res;
    }
}
