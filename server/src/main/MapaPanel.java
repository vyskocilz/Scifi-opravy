/*
 * Created by JFormDesigner on Tue Aug 14 23:13:54 CEST 2012
 */

package main;

import base.EntityChangeHandler;
import oprava.Oprava;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Zdenek Vyskocil
 */
public class MapaPanel extends JPanel {

    public static Image mapImage;
    public static Image crossImage;

    private ArrayList<Oprava> listOprav = new ArrayList<Oprava>();


    //override paint method of panel
    public void paint(Graphics g) {
        //draw the image
        g.setColor(Color.white);
        g.fillRect(0, 0, 1000, 1000);
        if (mapImage != null) {
            g.drawImage(mapImage, 0, 0, this);
        }
        g.setColor(Color.darkGray);
        for (Oprava oprava : listOprav) {
            if (canOpravaPaint(oprava)) {
                g.drawImage(crossImage, oprava.getCrossX(), oprava.getCrossY(), this);
                g.drawString(oprava.getNazev(), oprava.getCrossX() + 16 + 5, oprava.getCrossY() + 12);
            }
        }
    }

    public void addOprava(Oprava oprava) {
        if (!listOprav.contains(oprava)) {
            listOprav.add(oprava);
            oprava.addDataChangeHandler(new EntityChangeHandler() {
                public void onDataChange(Object object) {
                    repaint();
                }
            });
        }
        repaint();
    }

    private boolean canOpravaPaint(Oprava oprava) {
        return oprava.isShowCross() && oprava.getCrossX() != 0 && oprava.getCrossY() != 0;
    }

    public void removeOprava(Oprava oprava) {
        listOprav.remove(oprava);
    }

    public void removeOpravy() {
        listOprav.clear();
    }

    public MapaPanel() {
        initComponents();
        try {
            mapImage = ImageIO.read(new File("mapa.jpg"));
            Dimension dimension = new Dimension(((BufferedImage) mapImage).getWidth(), ((BufferedImage) mapImage).getHeight());
            this.setSize(dimension);
            this.setMinimumSize(dimension);
            this.setPreferredSize(dimension);
            crossImage = ImageIO.read(new File("cross.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license

        //======== this ========
        setBackground(Color.white);
        setLayout(new FlowLayout());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
