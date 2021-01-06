package rest.providers;

import rest.model.utils.AppException;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.MediaType;


@Provider
public class NotFoundMapper implements ExceptionMapper<NotFoundException> {
    @Override
    public Response toResponse(NotFoundException ex) {
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(new AppException(ex.getMessage(), Response.Status.NOT_FOUND))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
