package rest.utils;

import rest.model.utils.AppException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * WebException handler (gives code, reason, details)
 */
public class WebException extends WebApplicationException {
    public WebException(Response.Status status, String message) {
        super(createException(status, message));
    }

    public static Response createException(Response.Status status, String message) {
        return Response.status(status)
                .entity(new AppException(message, status))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
