package server;

import chat.Chat;
import data.ChatData;
import data.ClientAkce;
import data.Login;
import data.Logout;
import log.Log;
import main.Application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ServerHandler extends Thread {
    private Socket clientSock;
    private String cliAddr;
    private int port;
    boolean closeAfterDisconnect = false;

    private ClientGroup cg;    // shared by all threads
    List<ServerHandler> serverHandlers;


    public ServerHandler(Socket s, ClientGroup cg, List<ServerHandler> serverHandlers) {
        this.cg = cg;
        this.serverHandlers = serverHandlers;

        clientSock = s;
        cliAddr = clientSock.getInetAddress().getHostAddress();
        port = clientSock.getPort();
        Log.log("Client connection from (" + cliAddr + ", " + port + ")");
    }

    public void disconnect() {
        try {
            if (!clientSock.isClosed()) {
                clientSock.close();
                closeAfterDisconnect = true;
            }
        } catch (IOException e) {
            Log.error(e);
        }
    }

    public void run() {
        ServerClient client = null;
        try {
            serverHandlers.add(this);
            // Get I/O streams from the socket
            ObjectInputStream in = new ObjectInputStream(clientSock.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(clientSock.getOutputStream());
            client = cg.addClient(cliAddr, port, out);
            processClient(client, in, out);
            cg.removeClient(client);
            clientSock.close();
            if (!closeAfterDisconnect) {
                serverHandlers.remove(this);
            }
            Log.log("Client (" + cliAddr + ", " + port + ") connection closed\n");
        } catch (Exception e) {
            Log.error(e);
        }
        serverHandlers = null;
        clientSock = null;
        cg = null;
    }  // end of run()


    private void processClient(ServerClient client, ObjectInputStream in, ObjectOutputStream out)
        /* Stop when the input stream closes (is null) or "bye" is sent
  Otherwise pass the input to doRequest(). */ {
        Object data;
        try {
            client.setConnected(true);
            while (Server.isRunning() && client.isConnected()) {
                if (!((data = in.readObject()) == null)) {
                    if (data instanceof String) {
                        Log.log("Client (" + cliAddr + ", " +
                                port + "): " + data);
//                        doRequest((String) data, out);
                    } else if (data instanceof Login) {
                        Log.log("Uzivatel se prihlasil v: " + ((Login) data).getDatum());
                        client.setClientType(((Login) data).getClientName());
                        cg.fireUpdateChange(client);
                        Application.clientData.onLogin(client);
                    } else if (data instanceof Logout) {
                        Log.log("UZivatel se dobrovolne odhlasil");
                        client.setConnected(false);
                    } else if (data instanceof ChatData) {
//                        Log.log("UZivatel napsal");
                        Chat.writeText(client.getClientType(), ((ChatData) data).getText());
                    } else if (data instanceof ClientAkce) {
                        Application.clientData.onAction((ClientAkce) data);
                    }
                }
            }
        } catch (IOException e) {
            Log.error(e);
        } catch (ClassNotFoundException e) {
            Log.error(e);
        }
    }  // end of processClient()
}
