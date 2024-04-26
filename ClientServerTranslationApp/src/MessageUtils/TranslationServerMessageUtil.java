package MessageUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TranslationServerMessageUtil {

    public static String createResponseMessage(String response) {
        return "RESPONSE|" + response;
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

    public static void sendMessage(String word, int clientPort){
        try {
            Socket socket = new Socket("localhost",clientPort);
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            out.println(word);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}