package rest.resources;

import rest.dao.CityDAO;
import rest.model.City;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/cities")
public class CitiesResource {
    @Context
    private UriInfo uriInfo;
    @Context
    private Request request;

    private static final CityDAO cityDAO = new CityDAO();

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<City> getCities() {
        // Todo: Change this so it takes into account possible movies/id/cities/id and only select cities where the film is playing
        return cityDAO.selectAll();
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCount() {
        int count = cityDAO.selectAll().size();
        return String.valueOf(count);
    }

    @Path("{city}")
    public CityResource getCity(@PathParam("city") String id) {
        return new CityResource(uriInfo, request, Integer.parseInt(id));
    }
}
