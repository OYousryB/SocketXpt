package incorta.tcp;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MiddleMan {
    private MiddleMan(String address, int port1, int port2){
        DataInputStream in = null;
        DataOutputStream out = null;
        Socket socket = null;
        String line = "";

        try {
            socket = new Socket(address, port1);
            System.out.println("Connected With Spark Client");

            ServerSocket server = new ServerSocket(port2);
            System.out.println("Incorta Server started");
            System.out.println("Waiting for Actual Client ...");
            Socket clientSocket = server.accept();
            System.out.println("Connected!");

            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(clientSocket.getOutputStream());

        } catch (IOException e) {
            System.out.println(e);
        }
        while (!line.equals("Over")) {
            try {
                out.writeUTF(in.readUTF());
            } catch (Exception e) {
                line = "Over";
            }
        }

        try {
            out.close();
            socket.close();
        } catch(IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        new MiddleMan("127.0.0.1", 6000, 7000);
    }
}
