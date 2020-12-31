package rest.resources;

import rest.dao.TheaterDAO;
import rest.model.Theater;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.util.List;

import static java.lang.Integer.parseInt;

public class TheatersResource {
    @Context
    private UriInfo uriInfo;
    @Context
    private Request request;

    private int idCity;


    private TheaterDAO theaterDAO = new TheaterDAO();

    public TheatersResource() {
    }

    public TheatersResource(UriInfo uriInfo, Request request, int idCity) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.idCity = idCity;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Theater> getTheaters() {
        return theaterDAO.selectAllFromCity(idCity);
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCount() {
        int count = theaterDAO.selectAllFromCity(idCity).size();
        return String.valueOf(count);
    }

    @Path("{theater}")
    public TheaterResource getTheater(@PathParam("theater") String id) {
        return new TheaterResource(uriInfo, request, idCity, parseInt(id));
    }
}
