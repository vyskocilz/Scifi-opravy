package data;

import java.io.Serializable;
import java.util.List;

public class OpravyData implements Serializable {
    private String typ;
    List<OpravaData> opravy;

    public OpravyData() {
    }

    public OpravyData(List<OpravaData> opravy) {
        this.opravy = opravy;
    }

    public List<OpravaData> getOpravy() {
        return opravy;
    }

    public void setOpravy(List<OpravaData> opravy) {
        this.opravy = opravy;
    }
}
