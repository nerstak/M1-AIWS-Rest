package rest.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import rest.dao.ManagerDAO;
import rest.model.Manager;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

import static com.auth0.jwt.JWT.decode;
import static rest.utils.Constants.PROP_TOKEN_ISSUER;
import static rest.utils.Constants.PROP_TOKEN_VALIDITY;

/**
 * Handle actions on token
 */
public class JWTToken {
    private static final String ERROR_STRING = "SECRET_ERROR";
    private static final String SECRET = generateSecret();
    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes(StandardCharsets.UTF_8));

    private static final String AUTHENTICATION_SCHEME = "Bearer";
    private static final String CLAIM_DB_ID = "id";
    private static final String CLAIM_DB_USERNAME = "username";


    /**
     * Create a token
     * @param id Id of user
     * @param username Username of user
     * @return Token
     */
    public static String create(int id, String username) {
        try {
            // Creating token
            return JWT.create()
                    .withIssuer(PROP_TOKEN_ISSUER)
                    .withExpiresAt(new Date(new Date().getTime() + PROP_TOKEN_VALIDITY))
                    .withClaim(CLAIM_DB_USERNAME,username)
                    .withClaim(CLAIM_DB_ID,id)
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Verify a token
     * @param token Token
     * @return Decoded Token
     */
    public static DecodedJWT verify(String token) {
        try {
            // Creating verifier
            JWTVerifier v = JWT.require(algorithm)
                    .withIssuer(PROP_TOKEN_ISSUER)
                    .build();
            return v.verify(token);
        } catch (Exception e) {
            // Exception means that the token was NOT verified
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Validity of token
     * @param token Token
     * @return Validity
     */
    public static boolean validity(String token) {
        return verify(token) != null;
    }


    /**
     * Generate a secret key
     * @return Secret key
     */
    private static String generateSecret() {
        try {
            // Generation of key
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256, new SecureRandom());
            SecretKey secretKey = keyGen.generateKey();

            // Convert to UTF8 characters
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // In case something fails, we have a static key
        return ERROR_STRING;
    }

    /**
     * Generate manager from extracted token
     * @param token Token
     * @return Manager (may be null)
     */
    public static Manager generateManager(String token) {
        // Decoding
        DecodedJWT decoded = decode(token);

        // Verifying that the claim is here
        if(decoded.getClaim(CLAIM_DB_ID).isNull()) return null;
        int userID = decoded.getClaim(CLAIM_DB_ID).asInt();

        // Selecting manager
        return new ManagerDAO().selectID(userID);
    }

    /**
     * Extract token from header
     * @param authorizationHeader header
     * @return Token
     */
    public static String extractToken(String authorizationHeader) {
        return authorizationHeader
                .substring(AUTHENTICATION_SCHEME.length()).trim();
    }
}
