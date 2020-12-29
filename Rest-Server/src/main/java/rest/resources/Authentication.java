package rest.resources;

import rest.dao.MovieTheaterDAO;
import rest.model.Movie;
import rest.model.MovieTheater;
import rest.model.utils.AuthResponse;
import sun.misc.BASE64Encoder;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import javax.xml.bind.JAXBElement;

import static rest.utils.Constants.ERROR_CONNECTION_ERROR;

@Path("/auth")
public class Authentication {
    @Context
    private UriInfo uriInfo;
    @Context
    private Request request;

    MovieTheaterDAO movieTheaterDAO = new MovieTheaterDAO();

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response authenticate(JAXBElement<MovieTheater> mt) {
        AuthResponse res = new AuthResponse();
        MovieTheater mtDB = movieTheaterDAO.selectID(mt.getValue().getId());

        if(mtDB.getPassword().equals(mt.getValue().getPassword())) {
            String authString = mtDB.getId() + ":" + mtDB.getPassword();
            res.setToken(new BASE64Encoder().encode(authString.getBytes()));
            return Response.ok().entity(res).build();
        } else {
            res.setError(ERROR_CONNECTION_ERROR);
            return Response.status(Response.Status.UNAUTHORIZED).entity(res).build();
        }
    }
}
