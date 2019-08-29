package xptObj.tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {
    private Client(String address, int port) {
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        Socket socket = null;
        String line = "";

        try {
            socket = new Socket(address, port);
            System.out.println("Connected With Spark Client");
            in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));

            long start = System.currentTimeMillis();
            while (line != null) {
                try {
                    in.readObject();
    //                System.out.println(in.readObject());
                } catch (Exception e) {
                    line = null;
                }
            }
            long finish = System.currentTimeMillis();
            System.out.println("Finished in : " + ((finish - start)/ 1000) + " Secs");
            out.close();
            socket.close();
        } catch(IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        new Client("127.0.0.1", 6000);
    }
}
