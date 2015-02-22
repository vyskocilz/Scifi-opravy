package data;

import komponenty.OpravaKomponenta;
import main.MapaPanel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;

public class ClientDataModel {
    JPanel opravaPanel;
    JPanel opravaMainPanel;
    MapaPanel mapPanel;
    public Image mapa;
    public Image cross;
    HashMap<String, OpravaKomponenta> opravy = new HashMap<String, OpravaKomponenta>();

    public static ClientDataModel Instance = new ClientDataModel();

    public boolean chatEnabled = false;

    public void setOpravaPanel(JPanel opravaPanel, JPanel opravaMainPanel, MapaPanel mapPanel) {
        this.opravaPanel = opravaPanel;
        this.opravaMainPanel = opravaMainPanel;
        this.mapPanel = mapPanel;

    }

    public boolean isChatEnabled() {
        return chatEnabled;
    }

    public void setChatEnabled(boolean chatEnabled) {
        boolean old = this.chatEnabled;
        this.chatEnabled = chatEnabled;
        changeSupport.firePropertyChange("chatEnabled", old, chatEnabled);
    }

    public void setImages(ImageData data) {
        this.mapa = data.getMap().getImage();
        this.cross = data.getCross().getImage();
    }

    public void updateOprava(OpravaData opravaData) {
        OpravaKomponenta opravaKomponenta = opravy.get(opravaData.getId());
        mapPanel.updateOprava(opravaData);
        if (opravaKomponenta == null) {
            opravaKomponenta = new OpravaKomponenta();

            opravy.put(opravaData.getId(), opravaKomponenta);
            opravaPanel.add(opravaKomponenta);
        }
        opravaKomponenta.setNazev(opravaData.getNazev());
        opravaKomponenta.setPopis(opravaData.getPopis());
        opravaKomponenta.setBarva(opravaData.isVyreseno(), opravaData.isVystraha(), opravaData.isVarovani());
        opravaKomponenta.setVisible(opravaData.isViditelnost());

    }

    public void repaint() {
        opravaMainPanel.revalidate();
        Graphics graphics = mapPanel.getGraphics();
        graphics.drawImage(mapa, 0, 0, mapPanel);
        mapPanel.repaint();
    }

    public void updateOpravyPanel(OpravyData opravyData) {
        opravy.clear();
        opravaPanel.removeAll();
        mapPanel.removeOpravy();
        //TODO smazat jen ty, ktere nejsou v seznamu
        for (OpravaData opravaData : opravyData.getOpravy()) {
            updateOprava(opravaData);
        }
        opravaPanel.repaint();
    }

    private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
}