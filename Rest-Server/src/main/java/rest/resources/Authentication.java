package rest.resources;

import rest.dao.ManagerDAO;
import rest.model.Manager;
import rest.model.utils.AuthResponse;
import rest.utils.JWTToken;
import rest.utils.WebException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import javax.xml.bind.JAXBElement;

import static rest.utils.Constants.*;

@Path("/auth")
public class Authentication {
    @Context
    private UriInfo uriInfo;
    @Context
    private Request request;

    ManagerDAO managerDAO = new ManagerDAO();

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response authenticate(JAXBElement<Manager> m) {
        AuthResponse res = new AuthResponse();
        Manager mDB = managerDAO.selectUsername(m.getValue().getUsername());

        if(mDB == null) {
            throw new WebException(Response.Status.UNAUTHORIZED, ERROR_NOT_FOUND);
        }

        if(mDB.getPassword().equals(m.getValue().getPassword())) {
            res.setToken(JWTToken.create(mDB.getIdManager(), mDB.getUsername()));
            return Response.ok().entity(res).build();
        } else {
            throw new WebException(Response.Status.UNAUTHORIZED, ERROR_CONNECTION_ERROR);
        }
    }
}
