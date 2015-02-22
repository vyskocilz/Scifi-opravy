package main;

import oprava.Oprava;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SaveData implements Serializable {


    List<Oprava> opravy;

    public SaveData() {
    }

    public void fillZdroje(List<Oprava> list) {
        opravy = new ArrayList<Oprava>();
        for (Oprava element : list) {
            opravy.add(element);
        }
    }

    public List<Oprava> getOpravy() {
        return opravy;
    }

    public void setOpravy(List<Oprava> opravy) {
        this.opravy = opravy;
    }
}
