package rest.resources;

import rest.dao.ManagerDAO;
import rest.dao.MovieDAO;
import rest.dao.TheaterDAO;
import rest.model.Movie;
import rest.model.Theater;
import rest.model.TheaterWithManager;
import rest.utils.WebException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

import static rest.utils.Constants.ERROR_NOT_FOUND;

/**
 * Theaters Resource
 */
public class TheatersResource {
    @Context
    private UriInfo uriInfo;
    @Context
    private Request request;

    private int idCity;
    private Movie movie;


    private static TheaterDAO theaterDAO = new TheaterDAO();
    private static ManagerDAO managerDAO = new ManagerDAO();

    public TheatersResource() {
    }

    public TheatersResource(UriInfo uriInfo, Request request, int idCity) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.idCity = idCity;
    }

    public TheatersResource(UriInfo uriInfo, Request request, int idCity, int idMovie) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.idCity = idCity;
        this.movie = new MovieDAO().selectID(idMovie);;
    }

    private List<Theater> selectAll(int idCity) {
        if(movie != null) {
            return theaterDAO.selectAllFromCityMovie(idCity,movie.getIdMovie());
        }
        return theaterDAO.selectAllFromCity(idCity);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Theater> getTheaters() {
        return selectAll(idCity);
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCount() {
        int count = selectAll(idCity).size();
        return String.valueOf(count);
    }

    @Path("{theater}")
    public TheaterResource getTheater(@PathParam("theater") String idString) {
        try {
            int id = Integer.parseInt(idString);
            if(theaterDAO.selectID(id) != null) {
                if(movie != null) {
                    return new TheaterResource(uriInfo, request, idCity, id, movie.getIdMovie());
                }
                return new TheaterResource(uriInfo, request, idCity, id);
            }
        } catch (NumberFormatException ignored) {}

        throw new WebException(Response.Status.NOT_FOUND, ERROR_NOT_FOUND);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response postTheater(TheaterWithManager theaterWithManager) {
        Response res;
        res = Response.noContent().build();

        // Inserting theater
        if(theaterDAO.insert(theaterWithManager.getTheater())) {
            // Inserting manager if theater insertion was successful
            theaterWithManager.getManager().setIdTheater(theaterWithManager.getTheater().getId());
            managerDAO.insert(theaterWithManager.getManager());
        } else {
            throw new WebException(Response.Status.BAD_REQUEST, "Error while creating theater");
        }
        return res;
    }
}
