package rest.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

import static rest.utils.Constants.PROP_TOKEN_ISSUER;
import static rest.utils.Constants.PROP_TOKEN_VALIDITY;

/**
 * Handle actions on token
 */
public class JWTToken {
    private static final String ERROR_STRING = "SECRET_ERROR";
    private static final String SECRET = generateSecret();
    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes(StandardCharsets.UTF_8));

    /**
     * Create a token
     * @param id Id of user
     * @param username Username of user
     * @return Token
     */
    public static String create(String id, String username) {
        try {
            return JWT.create()
                    .withIssuer(PROP_TOKEN_ISSUER)
                    .withExpiresAt(new Date(new Date().getTime() + PROP_TOKEN_VALIDITY))
                    .withClaim("username",username)
                    .withClaim("id",id)
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
            JWTVerifier v = JWT.require(algorithm)
                    .withIssuer(PROP_TOKEN_ISSUER)
                    .build();
            return v.verify(token);
        } catch (JWTCreationException|IllegalArgumentException e) {
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
     * Decode a token
     * @param token Token
     * @return Decoded token
     */
    public static String decode(String token) {
        DecodedJWT d = verify(token);
        if(d != null) {
            return d.getToken();
        }
        return null;
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
        return ERROR_STRING;
    }
}
