package rest.resources;

import rest.dao.ActorDAO;
import rest.dao.MovieDAO;
import rest.model.Actor;
import rest.model.Movie;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.xml.bind.JAXBElement;

public class MovieResource {
    @Context
    private UriInfo uriInfo;
    @Context
    private Request request;
    private int id;

    private static final MovieDAO movieDAO = new MovieDAO();
    private static final ActorDAO actorDAO = new ActorDAO();

    public MovieResource() {
    }

    public MovieResource(UriInfo uriInfo, Request request, String id) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = Integer.parseInt(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Movie getMovie() {
        Movie m = movieDAO.selectID(id);
        if (m == null)
            throw new RuntimeException("Get: Movie with " + id + " not found");
        return m;
    }

    @DELETE
    public Response deleteMovie() {
        Movie m = movieDAO.selectID(id);

        if(m == null) return Response.status(Response.Status.NOT_FOUND).build();

        if(!movieDAO.delete(movieDAO.selectID(id))) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.OK).build();
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
        for (Actor a: movie.getActors()) {
            actorDAO.insert(a);
            movieDAO.addActorToMovie(movie, a);
        }
        return res;
    }
}
