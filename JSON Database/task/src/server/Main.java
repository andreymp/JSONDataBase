package server;

import java.util.Scanner;
import java.util.ArrayList;

class DataBase {

    private static ArrayList<String> dataBase = new ArrayList<>();

    public static void init() {
        for (int i = 0; i <= 101; i++) {
            dataBase.add("");
        }
    }

    private static String handleDelete(String[] command) throws Exception {
        int index= Integer.parseInt(command[1]) - 1;
        if (index < 0 || index > 99)
            throw new Exception("ERROR");
        if (!dataBase.get(index).equals("")) {
            dataBase.remove(dataBase.get(index));
            dataBase.set(index, "");
        }
        return "OK";
    }

    private static String handleGet(String[] command) throws Exception {
        int index = Integer.parseInt(command[1]) - 1;
        if (index < 0 || index > 99 || dataBase.get(index).equals(""))
            throw new Exception("ERROR");
        return dataBase.get(index);
    }

    private static String handleSet(String[] command) throws Exception {
        int index = Integer.parseInt(command[1]) - 1;
        if (index < 0 || index > 99)
            throw new Exception("ERROR");
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
        }
        throw new Exception("ERROR");
    }
}

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        DataBase.init();
        String command = "";
        while (true) {
            try {
                command = sc.nextLine();
                if (command.equals("exit"))
                    break;
                System.out.println(DataBase.handleInput(command));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
