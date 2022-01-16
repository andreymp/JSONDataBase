package server;

import java.util.ArrayList;
import java.util.HashMap;
import com.google.gson.*;

public class DataBase {
    private static HashMap<String, String> dataBase = new HashMap<>(10000);

    private static String handleDelete(String[] command) {
        if (dataBase.get(command[1]) == null)
            return "ERROR";
        dataBase.remove(dataBase.get(command[1]));
        dataBase.put(command[1], null);
        return "OK";
    }

    private static String handleGet(String[] command) {
        if (dataBase.get(command[1]) == null)
            return "ERROR";
        return "OK";
    }

    private static String handleSet(String[] command) {
        String text = "";
        for (int i = 2; i < command.length - 1; i++)
            text += command[i] + " ";
        text += command[command.length - 1];
        dataBase.put(command[1], text);
        return "OK";
    }

    public static String makeJSON(String input) {
        Gson gson = new Gson();
        HashMap<String, String> map = new HashMap<>();
        String response = handleInput(input);
        map.put("response", response);
        if (input.contains("get") && response.equals("OK"))
            map.put("value", dataBase.get(input.substring(4, input.lastIndexOf(" "))));
        else if (response.equals("ERROR") && !input.contains("set"))
            map.put("reason", "No such key");
        return gson.toJson(map);
    }

    public static String handleInput(String input) {
        if (input.startsWith("get")) {
            return handleGet(input.split(" "));
        } else if (input.startsWith("set")) {
            return handleSet(input.split(" "));
        } else if (input.startsWith("delete")) {
            return handleDelete(input.split(" "));
        } else if (input.startsWith("exit"))
            return "OK";
        return "ERROR";
    }
}
