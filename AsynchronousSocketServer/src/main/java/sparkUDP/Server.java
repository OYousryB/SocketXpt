package sparkUDP;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class Server {
    private Server(int port) {
        DatagramSocket ds;
        String line = "";
        DataInputStream rawData = null;

        try {
            rawData = new DataInputStream(new FileInputStream("/home/yousry/dummy_db/tpch/1/tbl/nation/lineitem.tbl.1"));

            ds = new DatagramSocket();
            InetAddress ip = InetAddress.getLocalHost();
            byte[] sender;

            Thread.sleep(10000);
            long start = System.currentTimeMillis();
            while (!line.equals("Over")) {
                try {
                    line = rawData.readLine();
                    sender = line.getBytes();
                    Thread.sleep(1000);

                    DatagramPacket DpSend = new DatagramPacket(sender, sender.length, ip, port);
                    ds.send(DpSend);
                } catch (Exception e) {
                    line = "Over";
                }
            }
            long finish = System.currentTimeMillis();
            System.out.println("Finished in : " + ((finish - start)/ 1000) + " Secs");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Server(1234);
    }
}