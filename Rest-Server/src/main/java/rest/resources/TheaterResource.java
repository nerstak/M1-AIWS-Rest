package rest.resources;

import rest.dao.TheaterDAO;
import rest.model.Manager;
import rest.model.Theater;
import rest.resources.filter.Secured;
import rest.utils.JWTToken;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.xml.bind.JAXBElement;

public class TheaterResource {
    @Context
    private UriInfo uriInfo;
    @Context
    private Request request;

    private int idCity;
    private int idTheater;

    private TheaterDAO theaterDAO = new TheaterDAO();

    public TheaterResource(UriInfo uriInfo, Request request, int idCity, int idTheater) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.idCity = idCity;
        this.idTheater = idTheater;
    }


    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Theater getTheater() {
        Theater m = theaterDAO.selectID(idTheater);
        if (m == null || m.getIdCity() != idCity)
            throw new RuntimeException("Get: Theater with idCity " + idCity + " and idTheater " + idTheater + " not found");
        return m;
    }

    @DELETE
    @Secured
    public Response deleteTheater(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        Theater m = theaterDAO.selectID(idTheater);
        Manager manager = JWTToken.generateManager(JWTToken.extractToken(authorizationHeader));

        if(m == null || m.getIdCity() != idCity) return Response.status(Response.Status.NOT_FOUND).build();

        if(manager == null || m.getId() != manager.getIdTheater())  return Response.status(Response.Status.UNAUTHORIZED).build();

        if(!theaterDAO.delete(theaterDAO.selectID(idTheater))) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Secured
    @Consumes(MediaType.APPLICATION_XML)
    public Response putTheater(JAXBElement<Theater> theater) {
        // Todo: Maybe a verification of super user or something?
        return putAndGetResponse(theater.getValue());
    }

    private Response putAndGetResponse(Theater theater) {
        Response res;
        res = Response.noContent().build();
/*
        movieDAO.insert(movie);
        for (Actor a: movie.getActors()) {
            actorDAO.insert(a);
            movieDAO.addActorToMovie(movie, a);
        }*/
        return res;
    }
}
