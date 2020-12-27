package rest.resources;

import rest.dao.MovieDAO;
import rest.model.Movie;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.xml.bind.JAXBElement;

public class MovieResource {
    @Context
    private final UriInfo uriInfo;
    @Context
    private final Request request;
    private int id;

    private static final MovieDAO movieDAO = new MovieDAO();

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

    @DELETE
    public void deleteMovie() {
        if(!movieDAO.delete(movieDAO.selectID(id))) {
            throw new RuntimeException("Delete: Movie with " + id + " not found");
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public Response putMovie(JAXBElement<Movie> movie) {
        return putAndGetResponse(movie.getValue());

    }

    private Response putAndGetResponse(Movie movie) {
        Response res;
        res = Response.noContent().build();

        movieDAO.insert(movie);
        return res;
    }
}
