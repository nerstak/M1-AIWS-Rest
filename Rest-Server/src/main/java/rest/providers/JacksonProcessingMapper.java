package rest.providers;

import com.fasterxml.jackson.core.JsonProcessingException;
import rest.utils.WebException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static rest.utils.Constants.ERROR_RESOURCE_PROVIDED;

@Provider
public class JacksonProcessingMapper implements ExceptionMapper<JsonProcessingException> {
    @Override
    public Response toResponse(JsonProcessingException e) {
        return WebException.createException(Response.Status.BAD_REQUEST, ERROR_RESOURCE_PROVIDED);
    }
}
