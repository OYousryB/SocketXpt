package trials;

import java.net.*;
import java.io.*;

public class Client3 {

    private Client3(String address, int port) {
        DataInputStream in = null;
        Socket socket;
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");

            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch(IOException i) {
            System.out.println(i);
        }

        while (true) {
            try {
                System.out.println(in.readUTF());
            }
            catch(Exception ignored) {
            }
        }
    }

    public static void main(String[] args) {
        new Client3("127.0.0.1", 6000);
    }
}