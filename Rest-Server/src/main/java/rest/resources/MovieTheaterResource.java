package rest.resources;

import rest.dao.MovieTheaterDAO;
import rest.model.Actor;
import rest.model.Movie;
import rest.model.MovieTheater;
import rest.resources.filter.Secured;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.xml.bind.JAXBElement;

public class MovieTheaterResource {
    @Context
    private UriInfo uriInfo;
    @Context
    private Request request;

    private int idCity;
    private int idMovieTheater;

    private MovieTheaterDAO movieTheaterDAO = new MovieTheaterDAO();

    public MovieTheaterResource(UriInfo uriInfo, Request request, int idCity, int idMovieTheater) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.idCity = idCity;
        this.idMovieTheater = idMovieTheater;
    }


    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public MovieTheater getMovieTheater() {
        MovieTheater m = movieTheaterDAO.selectID(idMovieTheater);
        if (m == null || m.getIdCity() != idCity)
            throw new RuntimeException("Get: MovieTheater with idCity " + idCity + " and idMovieTheater " + idMovieTheater + " not found");
        return m;
    }

    @DELETE
    @Secured
    public Response deleteMovieTheater(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        // Todo: Check if user is linked to movie theater
        MovieTheater m = movieTheaterDAO.selectID(idCity);

        if(m == null || m.getIdCity() != idCity) return Response.status(Response.Status.NOT_FOUND).build();

        if(!movieTheaterDAO.delete(movieTheaterDAO.selectID(idCity))) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Secured
    @Consumes(MediaType.APPLICATION_XML)
    public Response putMovieTheater(JAXBElement<MovieTheater> movieTheater) {
        // Todo: Check if user is linked to movie theater
        return putAndGetResponse(movieTheater.getValue());
    }

    private Response putAndGetResponse(MovieTheater movieTheater) {
        Response res;
        res = Response.noContent().build();
/*
        movieDAO.insert(movie);
        for (Actor a: movie.getActors()) {
            actorDAO.insert(a);
            movieDAO.addActorToMovie(movie, a);
        }*/
        return res;
    }
}
