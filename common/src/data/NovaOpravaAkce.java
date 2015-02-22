package data;

/**
 * Created by Zdenec    .
 * User: Zdenec
 * WWW BASKET TREBIC
 */
public class NovaOpravaAkce implements ClientAkce {

    String kod;

    public NovaOpravaAkce() {
    }

    public NovaOpravaAkce(String kod) {
        this.kod = kod;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }
}
