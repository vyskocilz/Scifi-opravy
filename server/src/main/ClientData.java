package main;

import chat.ChatServerData;
import data.ChatEnableData;
import data.ClientAkce;
import data.ImageData;
import data.NovaOpravaAkce;
import oprava.Oprava;
import oprava.Opravy;
import server.ServerClient;
import server.ServerUtils;

import javax.swing.*;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

import static javax.swing.JOptionPane.YES_OPTION;

public class ClientData implements Serializable {

    private Opravy opravy = new Opravy();
    private ChatServerData chatServerData = new ChatServerData();

    public void init() {
        opravy.init();

    }

    public void save(String file) {
        if (YES_OPTION == JOptionPane.showConfirmDialog(null, "Opravdu uložit data?", "Uložení dat", JOptionPane.YES_NO_OPTION)) {
            SaveData data = new SaveData();
            data.fillZdroje(opravy.getList());
            XMLEncoder e;
            try {
                e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)));
                e.writeObject(data);
                e.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    public void load(String file, MapaPanel mapaPanel) {
        if (YES_OPTION == JOptionPane.showConfirmDialog(null, "Opravdu nahrát data?", "Nahrání dat", JOptionPane.YES_NO_OPTION)) {
            XMLDecoder d = null;
            try {
                if (new File(file).exists()) {
                    XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)));
                    Object o = decoder.readObject();
                    if (o instanceof SaveData) {
                        SaveData data = (SaveData) o;
                        opravy.loadFromSave(data.getOpravy());
                        mapaPanel.removeOpravy();
                        for (Oprava oprava : opravy.getList()) {
                            mapaPanel.addOprava(oprava);
                        }

                    }
                    decoder.close();
                }
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }

    public Opravy getOpravy() {
        return opravy;
    }

    public void onLogin(ServerClient client) {
        ImageIcon map = new ImageIcon(MapaPanel.mapImage);
        ImageIcon cross = new ImageIcon(MapaPanel.crossImage);
        ServerUtils.getClientGroup().broadcast(new ImageData(cross, map));
        ServerUtils.getClientGroup().broadcast(new ChatEnableData(chatServerData.isChatEnabled()));
        opravy.sendElements();
    }


    public void onAction(ClientAkce akce) {
        if (akce instanceof NovaOpravaAkce) {
            opravy.doAction((NovaOpravaAkce) akce);
        }
    }

    public ChatServerData getChatServerData() {
        return chatServerData;
    }
}
