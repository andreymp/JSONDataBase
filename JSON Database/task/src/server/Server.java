package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final String address = "127.0.0.1";
    private static final int port = 4242;

    public static void connect() {
        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address))) {
            System.out.println("Server started!");
            while (true) {
                try {
                    Socket socket = server.accept();
                    DataInputStream input = new DataInputStream(socket.getInputStream());
                    DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                    String message = input.readUTF();
                    if (message.trim().equals("exit")) {
                        output.writeUTF("OK");
                        server.close();
                        break ;
                    }
                    String retValue = DataBase.handleInput(message);
                    output.writeUTF(retValue);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
