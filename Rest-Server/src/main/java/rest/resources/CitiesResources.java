package rest.resources;

import rest.dao.CityDAO;
import rest.model.City;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/cities")
public class CitiesResources {
    @Context
    private UriInfo uriInfo;
    @Context
    private Request request;

    private static final CityDAO cityDAO = new CityDAO();

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<City> getCities() {
        return cityDAO.selectAll();
    }
}
