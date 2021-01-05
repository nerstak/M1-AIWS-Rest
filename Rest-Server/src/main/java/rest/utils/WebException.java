package rest.utils;

import rest.model.AppException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class WebException extends WebApplicationException {
    public WebException(Response.Status status, String message) {
        super(Response.status(status)
                .entity(new AppException(message, status)).type(MediaType.APPLICATION_JSON).build());
    }
}
