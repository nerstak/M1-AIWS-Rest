package rest.resources;

import rest.dao.CityDAO;
import rest.dao.MovieDAO;
import rest.model.City;
import rest.model.Movie;
import rest.resources.filter.Secured;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.xml.bind.JAXBElement;

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
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public City getCity() {
        City c = select(idCity);
        if (c == null)
            throw new RuntimeException("Get: City with " + idCity + " not found");
        return c;
    }

    @DELETE
    @Secured
    public Response deleteCity() {
        City c = select(idCity);

        if(c == null) return Response.status(Response.Status.NOT_FOUND).build();

        if(!cityDAO.delete(cityDAO.selectID(idCity))) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Secured
    @Consumes(MediaType.APPLICATION_XML)
    public Response putCity(JAXBElement<City> city) {
        return putAndGetResponse(city.getValue());
    }

    private Response putAndGetResponse(City city) {
        Response res;
        res = Response.noContent().build();

        cityDAO.insert(city);

        return res;
    }

    @Path("theaters")
    public TheatersResource getTheaters() {
        return new TheatersResource(uriInfo, request, idCity);
    }

}
