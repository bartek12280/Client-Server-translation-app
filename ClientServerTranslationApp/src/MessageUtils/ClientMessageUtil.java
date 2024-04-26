package MessageUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientMessageUtil {
    public static String createRequestMessage(String word, String languageCode, int port) {
        return "REQUEST|" + word + "|" + languageCode;
    }

    public static String[] parseMessage(String message) {
        return message.split("\\|");
    }

    public static String getMessage(Socket socket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void sendMessage(String word, String languageCode, int port) {
        try {
            Socket socket = new Socket("localhost",port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(word + "|" + languageCode);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
