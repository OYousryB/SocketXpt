package incorta.udp;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class MiddleManWithObjects {
    private MiddleManWithObjects(int port1, int port2){
        String line = "";
        DatagramSocket ds;
        ObjectOutputStream out = null;

        try {
            ServerSocket server = new ServerSocket(port2);
            System.out.println("Incorta Server started");
            System.out.println("Waiting for Actual Client ...");
            Socket clientSocket = server.accept();
            System.out.println("Connected!");
            ds = new DatagramSocket(port1);
            byte[] receive = new byte[9965535];
            DatagramPacket DpReceive;
            out = new ObjectOutputStream(clientSocket.getOutputStream());

            while (!line.equals("Over")) {
                DpReceive = new DatagramPacket(receive, receive.length);
                try {
                    ds.receive(DpReceive);
                    byte[] data = DpReceive.getData();
                    ByteArrayInputStream in = new ByteArrayInputStream(data);
                    ObjectInputStream is = new ObjectInputStream(in);
                    out.writeObject(is.readObject());

//                    out.writeUTF(data(receive).toString());
                    receive = new byte[9965535];
                } catch (IOException e) {
                    line = "Over";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static StringBuilder data(byte[] a) {
        if (a == null) return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0) ret.append((char) a[i++]);
        return ret;
    }
    public static void main(String[] args) {
        new MiddleManWithObjects(1234, 7000);
    }

}