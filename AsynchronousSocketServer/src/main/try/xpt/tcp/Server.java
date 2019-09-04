package xpt.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private Server(int port) {
        DataInputStream rawData;
        DataOutputStream outStream;
        String tcpOut = "";
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Spark Server started");
            System.out.println("Waiting for Incorta Client ...");

            Socket socket = server.accept();
            outStream = new DataOutputStream(socket.getOutputStream());
            rawData = new DataInputStream(new FileInputStream("/home/yousry/dummy_db/tpch/10/tbl/lineitem/lineitem.txt"));

            System.out.println("Connected with Incorta Client");
//            Thread.sleep(10000);
            long start = System.currentTimeMillis();

            while (tcpOut != null) {
                try {
                    tcpOut = rawData.readLine();
                    outStream.writeUTF(tcpOut);
//                    Thread.sleep(1000);
                } catch (Exception e) {
                    tcpOut = null;
                }
            }
            System.out.println("Started at:" + start);
            long finish = System.currentTimeMillis();
            System.out.println("Finished in : " + ((finish - start)/ 1000) + " Secs");
            rawData.close();
            outStream.close();
            socket.close();

        } catch (Exception e){
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        new Server(6000);
    }

}

