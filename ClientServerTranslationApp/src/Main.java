import Client.Client;
import Server.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Client client = new Client();
        Server mainServer = null;
        try {
            mainServer = new Server(9090);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainServer.run();
        mainServer.stopProcessing();

//        client.sendRequestToMainServer("polska","EN", 7070);
    }
}
