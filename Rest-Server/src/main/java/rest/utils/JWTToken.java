package rest.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JWTToken {
    private static String SECRET = "THIS_IS_A_SECRET";
    private static Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes(StandardCharsets.UTF_8));

    public static String create(String id, String username) {
        try {
            return JWT.create()
                    .withIssuer("issuer")
                    .withExpiresAt(new Date(new Date().getTime() + 600))
                    .withSubject(username)
                    .withSubject(id)
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static DecodedJWT verify(String token) {
        try {
            JWTVerifier v = JWT.require(algorithm)
                    .withIssuer("issuer")
                    .build();
            return v.verify(token);
        } catch (JWTCreationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean validity(String token) {
        return verify(token) != null;
    }

    public static String decode(String token) {
        DecodedJWT d = verify(token);
        if(d != null) {
            return d.getToken();
        }
        return null;
    }
}
