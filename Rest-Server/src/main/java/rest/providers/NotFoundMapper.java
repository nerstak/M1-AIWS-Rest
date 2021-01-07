package rest.providers;

import rest.utils.WebException;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static rest.utils.Constants.ERROR_NOT_FOUND;

/**
 * Exception for 404
 */
@Provider
public class NotFoundMapper implements ExceptionMapper<NotFoundException> {
    @Override
    public Response toResponse(NotFoundException ex) {
        return WebException.createException(Response.Status.NOT_FOUND, ERROR_NOT_FOUND);
    }
}
