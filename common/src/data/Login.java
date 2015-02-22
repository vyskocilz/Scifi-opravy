package data;

import java.io.Serializable;
import java.util.Date;

public class Login implements Serializable {
    private Date datum;
    private String clientName;

    public Login() {

    }

    public Login(Date datum, String clientName) {
        this.clientName = clientName;
        this.datum = datum;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
