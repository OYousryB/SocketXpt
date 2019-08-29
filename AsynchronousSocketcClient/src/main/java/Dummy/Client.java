package Dummy;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    private Client(String address, int port) {
        DataInputStream in = null;
        Socket socket;
        try {
            socket = new Socket(address, port);
            System.out.println("Connected to incorta client");

            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch(IOException i) {
            System.out.println(i);
        }

        while (true) {
            try {
                String result = in.readUTF();
                System.out.println(result);
            }
            catch(Exception ignored) {
            }
        }
    }

    public static void main(String[] args) {
        new Client("127.0.0.1", 7000);
    }
}