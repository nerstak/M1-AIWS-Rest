package rest.resources;

import rest.dao.ScheduleDAO;
import rest.model.Manager;
import rest.model.Schedule;
import rest.model.Theater;
import rest.model.TheaterWithManager;
import rest.resources.filter.Secured;
import rest.utils.JWTToken;
import rest.utils.WebException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

import static rest.utils.Constants.ERROR_DELETE;
import static rest.utils.Constants.ERROR_NOT_FOUND;


public class ScheduleResource {
    @Context
    private UriInfo uriInfo;
    @Context
    private Request request;

    private int idSchedule;
    private int idTheater;
    private int idMovie;

    private static final ScheduleDAO scheduleDAO = new ScheduleDAO();

    public ScheduleResource(){};

    public ScheduleResource(UriInfo uriInfo, Request request, int idTheater, int idMovie, int idSchedule) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.idTheater = idTheater;
        this.idMovie = idMovie;
        this.idSchedule = idSchedule;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Schedule getSchedule() {
        Schedule s = scheduleDAO.selectID(idSchedule);

        if(s == null || s.getIdMovie()!=idMovie || s.getIdTheater()!=idTheater) {
            throw new WebException(Response.Status.NOT_FOUND, ERROR_NOT_FOUND);
        }
        return s;
    }

    @DELETE
    @Secured
    public Response deleteSchedule(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        Schedule s = scheduleDAO.selectID(idTheater);
        Manager manager = JWTToken.generateManager(JWTToken.extractToken(authorizationHeader));

        // Checking that schedule is in the correct theater and movie
        if(s == null || s.getIdTheater() != idTheater || s.getIdMovie() != idMovie)
            throw new WebException(Response.Status.NOT_FOUND, ERROR_NOT_FOUND);

        // Checking that the manager is linked to the theater
        if(manager == null || idTheater != manager.getIdTheater())
            throw new WebException(Response.Status.UNAUTHORIZED, ERROR_DELETE);

        if(!scheduleDAO.delete(scheduleDAO.selectID(idSchedule))) {
            throw new WebException(Response.Status.INTERNAL_SERVER_ERROR, ERROR_DELETE);
        }
        return Response.status(Response.Status.OK).build();
    }


    @PUT
    @Secured
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response putSchedule(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorizationHeader,Schedule schedule) {
        Response res;
        res = Response.noContent().build();

        Manager manager = JWTToken.generateManager(JWTToken.extractToken(authorizationHeader));

        // Checking that the manager is linked to the theater
        if(schedule== null || manager == null || idTheater != manager.getIdTheater())
            throw new WebException(Response.Status.UNAUTHORIZED, ERROR_DELETE);

        schedule.setIdTheater(idTheater);
        schedule.setIdMovie(idMovie);

        if(!scheduleDAO.insert(schedule)) {
            throw new WebException(Response.Status.BAD_REQUEST, "Error while creating theater");
        }
        return res;
    }
}