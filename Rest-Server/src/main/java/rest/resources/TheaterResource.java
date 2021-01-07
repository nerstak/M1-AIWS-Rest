package rest.resources;

import rest.dao.*;
import rest.model.*;
import rest.resources.filter.Secured;
import rest.utils.JWTToken;
import rest.utils.WebException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import static rest.utils.Constants.ERROR_DELETE;
import static rest.utils.Constants.ERROR_NOT_FOUND;

/**
 * Theater Resource
 */
public class TheaterResource {
    @Context
    private UriInfo uriInfo;
    @Context
    private Request request;

    private int idCity;
    private int idTheater;
    private Movie movie;

    private static TheaterDAO theaterDAO = new TheaterDAO();


    public TheaterResource() {
    }

    public TheaterResource(UriInfo uriInfo, Request request, int idCity, int idTheater) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.idCity = idCity;
        this.idTheater = idTheater;
    }

    public TheaterResource(UriInfo uriInfo, Request request, int idCity, int idTheater, int idMovie) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.idCity = idCity;
        this.idTheater = idTheater;
        this.movie = new MovieDAO().selectID(idMovie);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Theater getTheater() {
        Theater t = theaterDAO.selectID(idTheater);
        if (t == null || t.getIdCity() != idCity)
            throw new WebException(Response.Status.NOT_FOUND, ERROR_NOT_FOUND);
        return t;
    }

    @DELETE
    @Secured
    public Response deleteTheater(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        Theater t = theaterDAO.selectID(idTheater);
        Manager manager = JWTToken.generateManager(JWTToken.extractToken(authorizationHeader));

        // Checking that theater is in the selected city
        if(t == null || t.getIdCity() != idCity)
            throw new WebException(Response.Status.NOT_FOUND, ERROR_NOT_FOUND);

        // Checking that the manager is linked to the theater
        if(manager == null || t.getId() != manager.getIdTheater())
            throw new WebException(Response.Status.UNAUTHORIZED, ERROR_DELETE);

        if(!theaterDAO.delete(theaterDAO.selectID(idTheater))) {
            throw new WebException(Response.Status.INTERNAL_SERVER_ERROR, ERROR_DELETE);
        }
        return Response.status(Response.Status.OK).build();
    }

    @Path("schedules")
    public SchedulesResource getSchedules() {
        if(movie != null) {
            return new SchedulesResource(uriInfo, request, idTheater, movie.getIdMovie());
        } else {
            throw new WebException(Response.Status.NOT_FOUND, ERROR_NOT_FOUND);
        }
    }

    @Path("display")
    public MovieDisplayResource getMovieDisplay() {
        if(movie != null) {
            return new MovieDisplayResource(uriInfo, request, idTheater, movie.getIdMovie());
        } else {
            throw new WebException(Response.Status.NOT_FOUND, ERROR_NOT_FOUND);
        }
    }
}
