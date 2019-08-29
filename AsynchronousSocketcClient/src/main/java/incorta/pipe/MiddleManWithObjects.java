package incorta.pipe;

import objects.Nation;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MiddleManWithObjects {
    private MiddleManWithObjects(int port){
        ObjectOutputStream out;
        String echoResponse = "";

        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Incorta Server started");
            System.out.println("Waiting for Actual Client ...");
            Socket clientSocket = server.accept();
            System.out.println("Connected!");

            out = new ObjectOutputStream(clientSocket.getOutputStream());
//            RandomAccessFile pipe = new RandomAccessFile("/home/yousry/mypipe", "r");
            ObjectInputStream pipe = new ObjectInputStream(new FileInputStream("/home/yousry/mypipe"));

            while(echoResponse != null){
                out.writeObject(pipe.readObject());
            }
            pipe.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        new MiddleManWithObjects(7000);
    }


}
