package Server;


import MessageUtils.ServerMessageUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Objects;

public class Server implements Runnable {
    ServerSocket serverSocket;
    volatile boolean keepProcessing = true;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void run() {
        System.out.println("Start serwera głównego\n");

        while (keepProcessing) {
            try {
                Socket socket = serverSocket.accept();
                process(socket);
            } catch (Exception e) {
            }
        }
    }

    private void handle(Exception e) {
        if (!(e instanceof SocketException)) {
            e.printStackTrace();
        }
    }

    public void stopProcessing() {
        keepProcessing = false;
        closeIgnoringException(serverSocket);

    }

    private void closeIgnoringException(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException ignore) {
            }
        }
    }

    private void closeIgnoringException(ServerSocket serverSocket) {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException ignore) {
            }
        }
    }

    void process(Socket socket) {
        if (socket == null) return;
        try {
            System.out.println("Serwer: pobieranie komunikatu\n");
            String[] message = ServerMessageUtil.parseMessage(Objects.requireNonNull(ServerMessageUtil.getMessage(socket)));
            System.out.println(Arrays.toString(message));
            ServerMessageUtil.sendMessage(message[0], "localhost", message[1], "localhost", message[2]);
            closeIgnoringException(socket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



