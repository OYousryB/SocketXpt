package xpt.udp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.*;

public class Server {
    private Server(int port) {
        DataInputStream inputData;
        DatagramSocket udpSocket;
        String udpOut = "";

        try {
            inputData = new DataInputStream(new FileInputStream("/home/yousry/dummy_db/tpch/1/tbl/lineitem/lineitem.tbl.1"));
            udpSocket = new DatagramSocket();
            InetAddress ip = InetAddress.getLocalHost();
            byte[] sender;

            long start = System.currentTimeMillis();
            while (udpOut != null) {
                try {
                    udpOut = inputData.readLine();
                    sender = udpOut.getBytes();
//                    Thread.sleep(1000);

                    DatagramPacket DpSend = new DatagramPacket(sender, sender.length, ip, port);
                    udpSocket.send(DpSend);
                } catch (Exception e) {
                    udpOut = null;
                }
            }
            System.out.println("Started at:" + start);
            long finish = System.currentTimeMillis();
            System.out.println("Finished in : " + ((finish - start)/ 1000) + " Secs");
            inputData.close();
            udpSocket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Server(6000);
    }

}

