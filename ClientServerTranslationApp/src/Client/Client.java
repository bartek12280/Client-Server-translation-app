package Client;

import java.io.*;
import java.net.Socket;

public class Client extends Thread {
    private static final String MAIN_SERVER_ADDRESS = "localhost";
    private static final int MAIN_SERVER_PORT = 9090;

    public void sendRequestToMainServer(String wordToTranslate, String targetLanguage, int clientPort) {
        try {
            // Nawiązujemy połączenie z serwerem głównym
            Socket mainServerSocket = new Socket(MAIN_SERVER_ADDRESS, MAIN_SERVER_PORT);
            PrintWriter mainServerOut = new PrintWriter(mainServerSocket.getOutputStream(), true);
            BufferedReader mainServerIn = new BufferedReader(new InputStreamReader(mainServerSocket.getInputStream()));

            // Wysyłamy żądanie do serwera głównego
            mainServerOut.println(wordToTranslate + "," + targetLanguage + "," + clientPort);

            // Zamykamy połączenie z serwerem głównym
            mainServerSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
