package rest.model.utils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Authentication Response
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AuthResponse implements Serializable {
    private String token;

    public AuthResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
