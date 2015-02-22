package client;

import data.*;
import log.Log;

import java.io.ObjectInputStream;


public class ChatWatcher extends Thread {
    private Client client;   // ref to top-level client
    private ObjectInputStream ino;

    public ChatWatcher(Client c, ObjectInputStream ino) {
        client = c;
        this.ino = ino;
    }


    public void run() {
        Object line;
        try {
            while ((line = ino.readObject()) != null) {
                doRequest(line);
            }
        } catch (Exception e) {
            Log.log("Server disconected");
            Client.setConnected(false);
        }
    } // end of run()

    protected void doRequest(Object data) {
        if (data instanceof ChatData) {
            Chat.writeText(((ChatData) data).getText());
        }
        if (data instanceof OpravaData) {
            ClientDataModel.Instance.updateOprava((OpravaData) data);
            ClientDataModel.Instance.repaint();
        }
        if (data instanceof OpravyData) {
            ClientDataModel.Instance.updateOpravyPanel((OpravyData) data);
            ClientDataModel.Instance.repaint();
        }
        if (data instanceof ImageData) {
            ClientDataModel.Instance.setImages((ImageData) data);
        }
        if (data.getClass() == String.class) {
//          client.showMsg((String) data + "\n");
            Log.log(data);
        }
        if (data instanceof ChatEnableData) {
            ClientDataModel.Instance.setChatEnabled(((ChatEnableData) data).isEnabled());
        }
    }

}