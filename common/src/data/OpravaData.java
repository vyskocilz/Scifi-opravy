package data;

import java.io.Serializable;

/**
 * Created by Zdenec    .
 * User: Zdenec
 * WWW BASKET TREBIC
 */
public class OpravaData implements Serializable {
    String id;
    String nazev;
    String popis;
    private boolean viditelnost;
    private boolean vystraha;
    private boolean varovani;
    private boolean vyreseno;
    private int crossX;
    private int crossY;
    private boolean showCross;
    private boolean aktivni;

    public OpravaData() {
    }

    public OpravaData(String id, String nazev, String popis, boolean viditelnost, boolean vystraha, boolean varovani, boolean vyreseno, int crossX, int crossY, boolean showCross, boolean aktivni) {
        this.id = id;
        this.nazev = nazev;
        this.popis = popis;
        this.viditelnost = viditelnost;
        this.vystraha = vystraha;
        this.varovani = varovani;
        this.vyreseno = vyreseno;
        this.crossX = crossX;
        this.crossY = crossY;
        this.showCross = showCross;
        this.aktivni = aktivni;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public boolean isViditelnost() {
        return viditelnost;
    }

    public void setViditelnost(boolean viditelnost) {
        this.viditelnost = viditelnost;
    }

    public boolean isVystraha() {
        return vystraha;
    }

    public void setVystraha(boolean vystraha) {
        this.vystraha = vystraha;
    }

    public boolean isVarovani() {
        return varovani;
    }

    public void setVarovani(boolean varovani) {
        this.varovani = varovani;
    }

    public boolean isVyreseno() {
        return vyreseno;
    }

    public void setVyreseno(boolean vyreseno) {
        this.vyreseno = vyreseno;
    }

    public int getCrossX() {
        return crossX;
    }

    public void setCrossX(int crossX) {
        this.crossX = crossX;
    }

    public int getCrossY() {
        return crossY;
    }

    public void setCrossY(int crossY) {
        this.crossY = crossY;
    }

    public boolean isShowCross() {
        return showCross;
    }

    public void setShowCross(boolean showCross) {
        this.showCross = showCross;
    }

    public boolean isAktivni() {
        return aktivni;
    }

    public void setAktivni(boolean aktivni) {
        this.aktivni = aktivni;
    }
}
