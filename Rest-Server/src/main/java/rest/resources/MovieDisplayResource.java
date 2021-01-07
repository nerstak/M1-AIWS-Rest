package rest.resources;

import rest.dao.MovieDisplayDAO;
import rest.dao.ScheduleDAO;
import rest.model.Manager;
import rest.model.MovieDisplay;
import rest.model.Schedule;
import rest.resources.filter.Secured;
import rest.utils.JWTToken;
import rest.utils.WebException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import static rest.utils.Constants.*;
import static rest.utils.Constants.ERROR_POST;

public class MovieDisplayResource {
    @Context
    private UriInfo uriInfo;
    @Context
    private Request request;
    private int idMovie;
    private int idTheater;

    private static MovieDisplayDAO movieDisplayDAO = new MovieDisplayDAO();
    private static final ScheduleDAO scheduleDAO = new ScheduleDAO();


    public MovieDisplayResource(UriInfo uriInfo, Request request, int idMovie, int idTheater) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.idMovie = idMovie;
        this.idTheater = idTheater;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getDisplay() {
        MovieDisplay md = movieDisplayDAO.selectID(idMovie,idTheater);
        if(md == null)
            throw new WebException(Response.Status.NOT_FOUND, ERROR_NOT_FOUND);

        return Response.ok().entity(md).build();
    }

    @DELETE
    @Secured
    public Response deleteDisplay(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        MovieDisplay md = movieDisplayDAO.selectID(idMovie,idTheater);

        Manager manager = JWTToken.generateManager(JWTToken.extractToken(authorizationHeader));

        // Checking that display is in the correct theater and movie
        if(md == null || md.getIdTheater() != idTheater || md.getIdMovie() != idMovie)
            throw new WebException(Response.Status.NOT_FOUND, ERROR_NOT_FOUND);

        // Checking that the manager is linked to the theater
        if(manager == null || idTheater != manager.getIdTheater())
            throw new WebException(Response.Status.UNAUTHORIZED, ERROR_DELETE);

        if(!movieDisplayDAO.delete(md) || !scheduleDAO.delete(md)) {
            throw new WebException(Response.Status.INTERNAL_SERVER_ERROR, ERROR_DELETE);
        }

        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Secured
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response postDisplay(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorizationHeader, MovieDisplay movieDisplay) {
        Response res;
        res = Response.noContent().build();

        Manager manager = JWTToken.generateManager(JWTToken.extractToken(authorizationHeader));

        // Checking that the manager is linked to the theater
        if(movieDisplay == null || manager == null || idTheater != manager.getIdTheater())
            throw new WebException(Response.Status.UNAUTHORIZED, ERROR_AUTH_REQUIRED);

        movieDisplay.setIdTheater(idTheater);
        movieDisplay.setIdMovie(idMovie);

        if(!movieDisplayDAO.insert(movieDisplay)) {
            throw new WebException(Response.Status.BAD_REQUEST, ERROR_POST);
        }
        return res;
    }
}
