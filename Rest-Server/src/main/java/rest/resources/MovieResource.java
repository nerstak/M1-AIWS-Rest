package rest.resources;

import rest.dao.MovieDAO;
import rest.model.Movie;
import rest.resources.filter.Secured;
import rest.utils.WebException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import static rest.utils.Constants.ERROR_NOT_FOUND;

/**
 * Movie Resource
 */
public class MovieResource {
    @Context
    private UriInfo uriInfo;
    @Context
    private Request request;
    private int id;

    private static final MovieDAO movieDAO = new MovieDAO();

    public MovieResource() {
    }

    public MovieResource(UriInfo uriInfo, Request request, int id) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Movie getMovie() {
        Movie m = movieDAO.selectID(id);
        if (m == null)
            throw new WebException(Response.Status.NOT_FOUND, ERROR_NOT_FOUND);
        return m;
    }

    @DELETE
    @Secured
    public Response deleteMovie() {
        Movie m = movieDAO.selectID(id);

        if(m == null) return Response.status(Response.Status.NOT_FOUND).build();

        if(!movieDAO.delete(movieDAO.selectID(id))) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.OK).build();
    }

    @Path("cities")
    public CitiesResource getCities() {
        return new CitiesResource(uriInfo, request, id);
    }
}
