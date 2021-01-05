package rest.resources;

import rest.dao.CityDAO;
import rest.dao.MovieDAO;
import rest.model.City;
import rest.model.Movie;
import rest.utils.WebException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.List;

import static rest.utils.Constants.ERROR_NOT_FOUND;

@Path("/cities")
public class CitiesResource {
    @Context
    private UriInfo uriInfo;
    @Context
    private Request request;
    private Movie movie;
    private List<City> selectAll = cityDAO.selectAll();

    private static final CityDAO cityDAO = new CityDAO();

    public CitiesResource() {
    }

    public CitiesResource(UriInfo uriInfo, Request request, int movie) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.movie = new MovieDAO().selectID(movie);
        selectAll = cityDAO.selectAll(this.movie);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<City> getCities() {
        return selectAll;
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCount() {
        int count = selectAll.size();
        return String.valueOf(count);
    }

    @Path("{city}")
    public CityResource getCity(@PathParam("city") int id) {
        if(cityDAO.selectID(id) != null) {
            if(movie != null) {
                return new CityResource(uriInfo, request, id, movie.getIdMovie());
            }
            return new CityResource(uriInfo, request, id);
        }
        throw new WebException(Response.Status.NOT_FOUND, ERROR_NOT_FOUND);
    }
}
