package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private static final String address = "127.0.0.1";
    private static final int port = 4242;

    private static String parseInput(String[] args) {
        String result = new String("");
        for (String str : args) {
            if (!str.startsWith("-")) {
                result += str + " ";
            }
        }
        return result;
    }

    public static void connect(String[] args) {
        try (Socket client = new Socket(InetAddress.getByName(address), port)) {
            System.out.println("Client started!");
            try {
                DataInputStream input = new DataInputStream(client.getInputStream());
                DataOutputStream output = new DataOutputStream(client.getOutputStream());
                String message = parseInput(args);
                output.writeUTF(message);
                System.out.printf("Sent: %s\n", message);
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
