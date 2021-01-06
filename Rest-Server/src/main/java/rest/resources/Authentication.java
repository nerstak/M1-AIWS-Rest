package rest.resources;


import rest.dao.ManagerDAO;
import rest.model.Manager;
import rest.model.utils.AuthResponse;
import rest.utils.JWTToken;

import javax.json.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import javax.xml.bind.JAXBElement;


import java.io.StringReader;

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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticate(String jsonString) {
        AuthResponse res = new AuthResponse();
        try{
            JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();

            String username =  jsonObject.getString("username");
            String password = jsonObject.getString("password");
            Manager mDB = managerDAO.selectUsername(username);

            if(mDB == null) {
                res.setError(ERROR_REQUEST_INCORRECT);
                return Response.status(Response.Status.UNAUTHORIZED).entity(res).build();
            }

            if(mDB.getPassword().equals(password)) {
                res.setToken(JWTToken.create(mDB.getIdManager(), mDB.getUsername()));
                return Response.ok().entity(res).build();
            } else {
                res.setError(ERROR_CONNECTION_ERROR);
                return Response.status(Response.Status.UNAUTHORIZED).entity(res).build();
            }

        } catch (Exception e) {
            res.setError(ERROR_REQUEST_INCORRECT);
            return Response.status(Response.Status.UNAUTHORIZED).entity(res).build();
        }
    }


}
