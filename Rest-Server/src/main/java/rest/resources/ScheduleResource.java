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
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Schedule getSchedule() {
        Schedule s = scheduleDAO.selectID(idSchedule);

        if(s == null || s.getIdMovie()!=idMovie || s.getIdTheater()!=idTheater) {
            throw new WebException(Response.Status.NOT_FOUND, ERROR_NOT_FOUND);
        }
        return s;
    }

    //TODO put and delete
}