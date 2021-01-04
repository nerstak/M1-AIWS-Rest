package rest.resources;

import rest.dao.ManagerDAO;
import rest.model.Manager;
import rest.model.utils.AuthResponse;
import rest.utils.JWTToken;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import javax.xml.bind.JAXBElement;

import static rest.utils.Constants.ERROR_CONNECTION_ERROR;
import static rest.utils.Constants.ERROR_REQUEST_INCORRECT;

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
            res.setError(ERROR_REQUEST_INCORRECT);
            return Response.status(Response.Status.UNAUTHORIZED).entity(res).build();
        }

        if(mDB.getPassword().equals(m.getValue().getPassword())) {
            res.setToken(JWTToken.create(mDB.getIdManager(), mDB.getUsername()));
            return Response.ok().entity(res).build();
        } else {
            res.setError(ERROR_CONNECTION_ERROR);
            return Response.status(Response.Status.UNAUTHORIZED).entity(res).build();
        }
    }
}
