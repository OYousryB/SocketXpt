package xpt.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Client {
    private Client(int port1) {
        String udpIn = "";
        DatagramSocket udpSocket;
        try {
            System.out.println("Incorta Server started");

            udpSocket = new DatagramSocket(port1);
            byte[] receive = new byte[65535];
            DatagramPacket DpReceive;

            long start = System.currentTimeMillis();
            while (udpIn != null) {
                DpReceive = new DatagramPacket(receive, receive.length);
                try {
                    udpSocket.receive(DpReceive);
//                    System.out.println(new String(DpReceive.getData()).trim());

                    receive = new byte[65535];
                } catch (IOException e) {
                    udpIn = null;
                }
            }
            long finish = System.currentTimeMillis();
            System.out.println("Finished at:" + finish);
            System.out.println("Finished in : " + ((finish - start)/ 1000) + " Secs");
            udpSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client(6000);
    }
}

