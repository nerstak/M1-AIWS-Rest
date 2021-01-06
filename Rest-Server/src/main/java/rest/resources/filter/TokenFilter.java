package rest.resources.filter;


import rest.model.utils.AppException;
import rest.utils.JWTToken;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

import static rest.utils.Constants.ERROR_AUTH_REQUIRED;

/**
 * Filter for "Secured" annotation
 */
@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class TokenFilter implements ContainerRequestFilter {
    private static final String REALM = "Active actions on resources";
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Get the Authorization header from the request
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Validate the Authorization header
        if (!isTokenBasedAuthentication(authorizationHeader)) {
            abortWithUnauthorized(requestContext);
            return;
        }

        // Extract the token from the Authorization header
        String token = JWTToken.extractToken(authorizationHeader);


        // Validate the token
        if(!validateToken(token)) {
            abortWithUnauthorized(requestContext);
        }


    }

    /**
     * Verify if the token is a Bearer one
     * @param authorizationHeader Header
     * @return Assertion
     */
    private boolean isTokenBasedAuthentication(String authorizationHeader) {
        // Check if the Authorization header is valid
        // It must not be null and must be prefixed with "Bearer" plus a whitespace
        // The authentication scheme comparison must be case-insensitive
        return authorizationHeader != null && authorizationHeader.toLowerCase()
                .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    /**
     * Abort authentication
     * @param requestContext Contet
     */
    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        // Abort the filter chain with a 401 status code response
        // The WWW-Authenticate header is sent along with the response
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .header(HttpHeaders.WWW_AUTHENTICATE,
                                AUTHENTICATION_SCHEME + " realm=\"" + REALM + "\"")
                        .entity(new AppException(ERROR_AUTH_REQUIRED, Response.Status.UNAUTHORIZED))
                        .type(MediaType.APPLICATION_JSON)
                        .build()
        );
    }

    /**
     * Validate a token
     * @param token Token
     * @return Assertion
     */
    private boolean validateToken(String token)  {
        // Check if the token was issued by the server and if it's not expired
        return JWTToken.validity(token);
    }
}
