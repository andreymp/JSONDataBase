package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import com.google.gson.*;
import com.google.gson.reflect.*;
import java.lang.reflect.Type;

public class Server {
    private static final String address = "127.0.0.1";
    private static final int port = 4242;
    private static HashMap<String, String> fromJSON = new HashMap<>();
    private static Gson gson = new Gson();

    private static void parseJSON(String input) {
        Type type = new TypeToken<HashMap<String, String>>(){}.getType();
        fromJSON = gson.fromJson(input, type);
    }

    private static String makeMessage() {
         return fromJSON.get("type") + " " + fromJSON.get("key") + " " + fromJSON.get("value");
    }

    public static void connect() {
        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address))) {
            System.out.println("Server started!");
            while (true) {
                HashMap<String, String> outputMap = new HashMap<>();
                try {
                    Socket socket = server.accept();
                    DataInputStream input = new DataInputStream(socket.getInputStream());
                    DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                    parseJSON(input.readUTF());
                    if (fromJSON.containsValue("exit")) {
                        outputMap.put("response", "OK");
                        output.writeUTF(gson.toJson(outputMap));
                        server.close();
                        break ;
                    }
                    output.writeUTF(DataBase.makeJSON(makeMessage()));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
