package rest.resources;

import rest.dao.MovieDAO;
import rest.model.Movie;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

public class MovieResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    int id;

    private static MovieDAO movieDAO = new MovieDAO();

    public MovieResource(UriInfo uriInfo, Request request, String id) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = Integer.parseInt(id);
    }

    //Application integration
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Movie getTodo() {
        Movie m = movieDAO.selectID(id);
        if (m == null)
            throw new RuntimeException("Get: Movie with " + id + " not found");
        return m;
    }
}
