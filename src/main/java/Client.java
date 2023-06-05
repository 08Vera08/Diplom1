import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String sendMessage(String msg) {
        out.println(msg);
        String response = null;
        try {
            response = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public void stopConnection() {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.close();
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.startConnection("127.0.0.1", 8989);
        Scanner scanner = new Scanner(System.in);
        String response = client.sendMessage(scanner.nextLine());

        ArrayList<String> responses = new ArrayList<>(Arrays.asList(response.split(", ")));
        responses.set(0, responses.get(0).replace("[", ""));
        responses.set(responses.size() - 1, responses.get(0).replace("]", ""));
        System.out.println(responses);
        if (!responses.isEmpty()) {
            ArrayList<JSONObject> jsonObjects = new ArrayList<>();
            for (int i = 0; i < responses.size(); ++i) {
                if (responses.get(i).isEmpty()) System.out.println(1);
                JSONObject jsonObject = new JSONObject(responses.get(i));
                jsonObjects.add(jsonObject);
            }

            System.out.println(jsonObjects);
        } else {
            System.out.println(Collections.emptyList());
        }

    }
}