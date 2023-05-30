import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
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

        JSONArray jsonArray = new JSONArray(response);

        if (!jsonArray.isEmpty()) {
            System.out.println('[');
            for (int i = 0; i < jsonArray.length(); ++i) {
                System.out.print(jsonArray.get(i));
            }
            System.out.println(']');
        } else {
            System.out.println("[]");
        }

    }
}