package server;

import java.util.ArrayList;

public class DataBase {
    private static ArrayList<String> dataBase = new ArrayList<>(10000);

    public static void init() {
        for (int i = 0; i <= 2000; i++) {
            dataBase.add("");
        }
    }

    private static String handleDelete(String[] command) {
        int index= Integer.parseInt(command[1]) - 1;
        if (index < 0 || index > 999)
            return "ERROR";
        if (!dataBase.get(index).equals("")) {
            dataBase.remove(dataBase.get(index));
            dataBase.set(index, "");
        }
        return "OK";
    }

    private static String handleGet(String[] command) throws Exception {
        int index = Integer.parseInt(command[1]) - 1;
        if (index < 0 || index > 999 || dataBase.get(index).equals(""))
            return "ERROR";
        return dataBase.get(index);
    }

    private static String handleSet(String[] command) throws Exception {
        int index = Integer.parseInt(command[1]) - 1;
        if (index < 0 || index > 999)
            return "ERROR";
        String text = "";
        for (int i = 2; i < command.length; i++)
            text += command[i] + " ";
        dataBase.set(index, text);
        return "OK";
    }

    public static String handleInput(String input) throws Exception {
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
