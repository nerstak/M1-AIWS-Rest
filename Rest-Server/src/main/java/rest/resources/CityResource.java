package rest.resources;

import rest.dao.CityDAO;
import rest.dao.MovieDAO;
import rest.model.City;
import rest.model.Movie;
import rest.resources.filter.Secured;
import rest.utils.WebException;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

import static rest.utils.Constants.ERROR_DELETE;
import static rest.utils.Constants.ERROR_NOT_FOUND;

/**
 * City Resource
 */
public class CityResource {
    @Context
    private UriInfo uriInfo;
    @Context
    private Request request;
    private int idCity;
    private Movie movie;


    private static final CityDAO cityDAO = new CityDAO();

    public CityResource() {
    }

    public CityResource(UriInfo uriInfo, Request request, int idCity) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.idCity = idCity;
    }

    public CityResource(UriInfo uriInfo, Request request, int idCity, int idMovie) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.idCity = idCity;
        this.movie = new MovieDAO().selectID(idMovie);
    }

    private City select(int idCity) {
        if(movie != null) {
            return cityDAO.selectID(idCity, movie);
        }
        return cityDAO.selectID(idCity);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public City getCity() {
        City c = select(idCity);
        if (c == null)
            throw new WebException(Response.Status.NOT_FOUND, ERROR_NOT_FOUND);
        return c;
    }

    @DELETE
    @Secured
    public Response deleteCity() {
        City c = select(idCity);

        if(c == null) throw new WebException(Response.Status.NOT_FOUND, ERROR_NOT_FOUND);


        if(!cityDAO.delete(cityDAO.selectID(idCity))) {
            throw new WebException(Response.Status.INTERNAL_SERVER_ERROR, ERROR_DELETE);
        }
        return Response.ok().build();
    }

    @Path("theaters")
    public TheatersResource getTheaters() {
        if(movie != null) {
            return new TheatersResource(uriInfo, request, idCity, movie.getIdMovie());
        }
        return new TheatersResource(uriInfo, request, idCity);
    }
}
