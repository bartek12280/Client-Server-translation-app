package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MainServer implements Runnable {
    private static final int PORT = 9090;
    private static final Map<String, String> languageServers = new HashMap<>();

    public MainServer() {
        languageServers.put("PL", "9091");
        languageServers.put("EN", "9092");
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Main server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);


                Thread clientThread = new Thread(() -> handleClientRequest(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClientRequest(Socket clientSocket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);


            String request = in.readLine();
            System.out.println("Received request from client: " + request);


            String[] requestData = request.split(",");
            String wordToTranslate = requestData[0];
            String targetLanguage = requestData[1];
            int clientPort = Integer.parseInt(requestData[2]);


            if (!languageServers.containsKey(targetLanguage)) {
                out.println("Error: Invalid language code");
                clientSocket.close();
                return;
            }


            String languageServerAddress = languageServers.get(targetLanguage);
            forwardRequestToLanguageServer(wordToTranslate, clientPort, languageServerAddress);


            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void forwardRequestToLanguageServer(String wordToTranslate, int clientPort, String languageServerAddress) {
        try {
            // Nawiązujemy połączenie z serwerem językowym
            Socket languageServerSocket = new Socket(languageServerAddress.split(":")[0], Integer.parseInt(languageServerAddress.split(":")[1]));
            PrintWriter out = new PrintWriter(languageServerSocket.getOutputStream(), true);

            // Wysyłamy zapytanie do serwera językowego
            out.println(wordToTranslate + "," + clientPort);

            // Zamykamy połączenie z serwerem językowym
            languageServerSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
