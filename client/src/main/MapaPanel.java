/*
 * Created by JFormDesigner on Tue Aug 14 23:13:54 CEST 2012
 */

package main;

import data.ClientDataModel;
import data.OpravaData;
import sun.awt.image.ToolkitImage;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * @author Zdenek Vyskocil
 */
public class MapaPanel extends JPanel {

    private HashMap<String, OpravaData> opravy = new HashMap<String, OpravaData>();

    //override paint method of panel
    public void paint(Graphics g) {
        //draw the image
        g.setColor(Color.white);
        g.fillRect(0, 0, 1000, 1000);
        if (ClientDataModel.Instance.mapa != null) {
            g.drawImage(ClientDataModel.Instance.mapa, 0, 0, this);
            Dimension dimension = new Dimension(((ToolkitImage) ClientDataModel.Instance.mapa).getWidth(), ((ToolkitImage) ClientDataModel.Instance.mapa).getHeight());
            this.setSize(dimension);
            this.setMinimumSize(dimension);
            this.setPreferredSize(dimension);
        }
        g.setColor(Color.darkGray);
        for (OpravaData oprava : opravy.values()) {
            if (canOpravaPaint(oprava)) {
                g.drawImage(ClientDataModel.Instance.cross, oprava.getCrossX(), oprava.getCrossY(), this);
                g.drawString(oprava.getNazev(), oprava.getCrossX() + 16 + 5, oprava.getCrossY() + 12);
            }
        }
    }

    public void updateOprava(OpravaData oprava) {
        opravy.put(oprava.getId(), oprava);
        repaint();
    }

    private boolean canOpravaPaint(OpravaData oprava) {
        return oprava.isShowCross() && oprava.isAktivni() && oprava.getCrossX() != 0 && oprava.getCrossY() != 0;
    }

    public void removeOpravy() {
        opravy.clear();
    }

    public MapaPanel() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license

        //======== this ========
        setLayout(new BorderLayout());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
