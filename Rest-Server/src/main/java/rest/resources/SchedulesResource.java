package rest.resources;

import rest.dao.MovieDisplayDAO;
import rest.dao.ScheduleDAO;
import rest.model.Manager;
import rest.model.Schedule;
import rest.resources.filter.Secured;
import rest.utils.JWTToken;
import rest.utils.WebException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

import static rest.utils.Constants.*;


public class SchedulesResource {
    @Context
    private UriInfo uriInfo;
    @Context
    private Request request;

    private int idTheater;
    private int idMovie;

    private static final ScheduleDAO scheduleDAO = new ScheduleDAO();
    private static final MovieDisplayDAO movieDisplayDAO = new MovieDisplayDAO();

    public SchedulesResource(){};

    public SchedulesResource(UriInfo uriInfo, Request request, int idTheater, int idMovie) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.idTheater = idTheater;
        this.idMovie = idMovie;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Schedule> getSchedules() {
        return scheduleDAO.selectAll(idMovie,idTheater);
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCount() {
        int count = scheduleDAO.selectAll().size();
        return String.valueOf(count);
    }

    @Path("{schedule}")
    public ScheduleResource getSchedule(@PathParam("schedule") String idString) {
        try {
            int id = Integer.parseInt(idString);
            if(scheduleDAO.selectID(id) != null) {
                return new ScheduleResource(uriInfo, request,idTheater,idMovie, id);
            }
        } catch (NumberFormatException ignored) { }
        throw new WebException(Response.Status.NOT_FOUND, ERROR_NOT_FOUND);
    }

    @POST
    @Secured
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response postSchedule(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorizationHeader,Schedule schedule) {
        Response res;
        res = Response.noContent().build();

        Manager manager = JWTToken.generateManager(JWTToken.extractToken(authorizationHeader));

        // Checking that the manager is linked to the theater
        if(schedule== null || manager == null || idTheater != manager.getIdTheater())
            throw new WebException(Response.Status.UNAUTHORIZED, ERROR_AUTH_REQUIRED);

        schedule.setIdTheater(idTheater);
        schedule.setIdMovie(idMovie);

        if(movieDisplayDAO.selectID(idMovie, idTheater) == null || !scheduleDAO.insert(schedule)) {
            throw new WebException(Response.Status.BAD_REQUEST, ERROR_POST);
        }
        return res;
    }
}