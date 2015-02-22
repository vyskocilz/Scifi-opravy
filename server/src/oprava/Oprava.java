package oprava;

import base.Entity;

public class Oprava extends Entity {

    String popis;
    String kod;

    boolean aktivni = false;
    boolean vyresena = false;
    boolean viditelnost = false;
    boolean varovani = false;
    boolean vystraha = false;
    int crossX = 0;
    int crossY = 0;
    boolean showCross = false;

    public Oprava() {
        super("Oprava");
    }

    public Oprava(String nazev) {
        this();
        this.nazev = nazev;

    }


    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
        onDataChange();
    }

    public boolean isAktivni() {
        return aktivni;
    }

    public void setAktivni(boolean aktivni) {
        this.aktivni = aktivni;
        onDataChange();
    }

    public boolean isVyresena() {
        return vyresena;
    }

    public void setVyresena(boolean vyresena) {
        this.vyresena = vyresena;
        onDataChange();
    }

    public boolean isViditelnost() {
        return viditelnost;
    }

    public void setViditelnost(boolean viditelnost) {
        this.viditelnost = viditelnost;
        onDataChange();
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
        onDataChange();
    }

    public boolean isVarovani() {
        return varovani;
    }

    public void setVarovani(boolean varovani) {
        this.varovani = varovani;
        onDataChange();
    }

    public boolean isVystraha() {
        return vystraha;
    }

    public void setVystraha(boolean vystraha) {
        this.vystraha = vystraha;
        onDataChange();
    }

    public int getCrossX() {
        return crossX;
    }

    public void setCrossX(int crossX) {
        this.crossX = crossX;
        onDataChange();
    }

    public int getCrossY() {
        return crossY;
    }

    public void setCrossY(int crossY) {
        this.crossY = crossY;
        onDataChange();
    }

    public boolean isShowCross() {
        return showCross;
    }

    public void setShowCross(boolean showCross) {
        this.showCross = showCross;
        onDataChange();
    }
}
