package rest.resources;

import rest.dao.ScheduleDAO;
import rest.model.Schedule;
import rest.utils.WebException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.List;

import static rest.utils.Constants.ERROR_NOT_FOUND;


public class SchedulesResource {
    @Context
    private UriInfo uriInfo;
    @Context
    private Request request;

    private int idTheater;
    private int idMovie;

    private static final ScheduleDAO scheduleDAO = new ScheduleDAO();

    public SchedulesResource(){};

    public SchedulesResource(UriInfo uriInfo, Request request, int idTheater, int idMovie) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.idTheater = idTheater;
        this.idMovie = idMovie;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
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
                return new ScheduleResource(uriInfo, request,idMovie,idTheater, id);
            }
        } catch (NumberFormatException ignored) { }
        throw new WebException(Response.Status.NOT_FOUND, ERROR_NOT_FOUND);
    }
}