package trials;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client2 {

    // constructor to put ip address and port
    private Client2(String address, int port) {
        DataOutputStream out = null;
        DataInputStream input = null;
        DataInputStream in = null;

        Socket socket = null;
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");

            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());

            input = new DataInputStream(System.in);
        } catch(IOException i) {
            System.out.println(i);
        }

        String line = "";
        while (!line.equals("Over")) {
            try {
                line = input.readLine();
                out.writeUTF(line);
                in.readUTF();
//                System.out.println(in.readUTF());
            }
            catch(Exception i) {
                line = "Over";
            }
        }

        try {
            input.close();
            out.close();
            socket.close();
        } catch(IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        new Client2("127.0.0.1", 6000);
    }
}