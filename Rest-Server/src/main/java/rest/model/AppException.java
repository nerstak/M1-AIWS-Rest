package rest.model;

import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement (name = "error")
public class AppException implements Serializable {
    private int statusCode;
    private String reason;
    private String details;

    public AppException() {
    }

    public AppException(String error, Response.Status status) {
        this.details = error;
        this.statusCode = status.getStatusCode();
        this.reason = status.getReasonPhrase();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
