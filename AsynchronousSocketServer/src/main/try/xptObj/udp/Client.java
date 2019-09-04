package xptObj.udp;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {
    private Client(String address, int port) {
        String line = "";
        DatagramSocket ds;

        try {
            System.out.println("Connected!");
            ds = new DatagramSocket(port);
            byte[] receive = new byte[99999];
            DatagramPacket DpReceive;

            int count = 0;
            while (!line.equals("Over")) {
                DpReceive = new DatagramPacket(receive, receive.length);
                try {
                    ds.receive(DpReceive);
                    byte[] data = DpReceive.getData();
                    ObjectInputStream is = new ObjectInputStream(new ByteArrayInputStream(data));
                    is.readObject();
                    System.out.println("another "+ count++ + " packet");
//                    System.out.println(is.readObject());
                } catch (IOException e) {
                    line = "Over";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client("127.0.0.1", 6000);
    }
}
