package client;

import java.util.HashMap;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import com.google.gson.Gson;

public class Client {
    private static final String address = "127.0.0.1";
    private static final int port = 4242;
    private static HashMap<String, String> storeOptions = new HashMap<>();
    private static Gson gson = new Gson();

    private static String[] parseInput(String[] args) {
        int counter = 0;
        for (String str : args) {
            if (!str.startsWith("-")) {
                counter++;
            }
        }
        String[] result = new String[counter];
        int i = 0;
        for (String str : args) {
            if (!str.startsWith("-")) {
                result[i++] = str;
            }
        }
        return result;
    }

    private static String makeJSON(String[] message) {
        storeOptions.put("type", message[0]);
        if (message.length > 1)
            storeOptions.put("key", message[1]);
        if (message.length > 2) {
            String msg = "";
            for (int i = 2; i < message.length - 1; i++)
                msg += message[i] + " ";
            msg += message[message.length - 1];
            storeOptions.put("value", msg);
        }
        return gson.toJson(storeOptions);
    }

    public static void connect(String[] args) {
        try (Socket client = new Socket(InetAddress.getByName(address), port)) {
            System.out.println("Client started!");
            try {
                DataInputStream input = new DataInputStream(client.getInputStream());
                DataOutputStream output = new DataOutputStream(client.getOutputStream());
                String json = makeJSON(parseInput(args));
                output.writeUTF(json);
                System.out.printf("Sent: %s\n", json);
                System.out.printf("Received: %s\n", input.readUTF());
                client.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
