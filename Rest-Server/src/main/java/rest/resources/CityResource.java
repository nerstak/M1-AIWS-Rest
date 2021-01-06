package rest.resources;

import rest.dao.CityDAO;
import rest.dao.MovieDAO;
import rest.model.City;
import rest.model.Movie;
import rest.resources.filter.Secured;
import rest.utils.WebException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.xml.bind.JAXBElement;

import static rest.utils.Constants.*;

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

    @PUT
    @Secured
    @Consumes(MediaType.APPLICATION_XML)
    public Response putCity(JAXBElement<City> city) {
        return putAndGetResponse(city.getValue());
    }

    private Response putAndGetResponse(City city) {
        Response res;
        res = Response.ok().build();

        if(!cityDAO.insert(city)) throw new WebException(Response.Status.INTERNAL_SERVER_ERROR, ERROR_PUT);

        return res;
    }

    @Path("theaters")
    public TheatersResource getTheaters() {
        if(movie != null) {
            return new TheatersResource(uriInfo, request, idCity, movie.getIdMovie());
        }
        return new TheatersResource(uriInfo, request, idCity);
    }
}
