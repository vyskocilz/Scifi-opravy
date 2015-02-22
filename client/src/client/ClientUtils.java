package client;

import data.NovaOpravaAkce;

public class ClientUtils {
    static Client client = new Client();

    public static void start() {
        client.start();
    }

    public static void stop() {
        client.stop();

    }

    public static void sendData(Object data) {
        client.sendData(data);
    }

    public static void sendKod(String kod) {
        if (kod == null || kod.length() == 0) {
            return;
        }
        client.sendData(new NovaOpravaAkce(kod));
    }
}
