package incorta.tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MiddleManWithObjects {
    private MiddleManWithObjects(String address, int port1, int port2){
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        Socket socket = null;
        String line = "";

        try {
            socket = new Socket(address, port1);
            System.out.println("Connected With Spark Client");

            ServerSocket server = new ServerSocket(port2);
            System.out.println("Incorta Server started");
            System.out.println("Waiting for Actual Client ...");
            Socket clientSocket = server.accept();

            in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new ObjectOutputStream(clientSocket.getOutputStream());

        } catch (IOException e) {
            System.out.println(e);
        }
        while (!line.equals("Over")) {
            try {
                out.writeObject(in.readObject());
//                System.out.println();
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
        new MiddleManWithObjects("127.0.0.1", 6000, 7000);
    }
}
