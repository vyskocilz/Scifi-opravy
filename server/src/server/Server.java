package server;

import ini.ServerProperty;
import log.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    static int PORT = 1234;  // port for this server

    private ClientGroup clientGroup;
    private static boolean running = false;
    ServerSocket serverSock;
    List<ServerHandler> serverHandlers;

    public Server() {
        clientGroup = new ClientGroup();
        serverHandlers = new ArrayList<ServerHandler>();
        PORT = Integer.parseInt(ServerProperty.getProperty(ServerProperty.SERVER_PORT));
    }  // end of Server()

    public void start() {
        if (Server.isRunning()) return;
        try {

            serverSock = new ServerSocket(PORT);
            setRunning(true);
            while (isRunning()) {
                Log.log("Waiting for a client...");
                Socket clientSock = serverSock.accept();
                ServerHandler serverHandler = new ServerHandler(clientSock, clientGroup, serverHandlers);
                serverHandler.start();

            }
        } catch (Exception e) {
            Log.error(e);
        }
    }

    public void stop() {
        if (isRunning()) {
            setRunning(false);
            try {
                serverSock.close();
                serverSock = null;
            } catch (IOException e) {
                Log.error(e);
            }
            for (ServerHandler serverHandler : serverHandlers) {
                serverHandler.disconnect();
                serverHandler.interrupt();
            }
            serverHandlers.clear();
        }
    }

    public static boolean isRunning() {
        return running;
    }

    public static void setRunning(boolean running) {
        Server.running = running;
    }

    public ClientGroup getClientGroup() {
        return clientGroup;
    }
}