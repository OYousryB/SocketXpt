package xpt.tcp;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.net.Socket;

public class Client {
    private Client(String address, int port) {
        DataInputStream inStream;
        Socket socket;
        String tcpIn = "";
        try {
            socket = new Socket(address, port);
            System.out.println("Connected With Spark Client");
            inStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            long start = System.currentTimeMillis();

            while (tcpIn != null) {
                try {
                    inStream.readUTF();
//                    System.out.println(inStream.readUTF());
                } catch (Exception e) {
                    tcpIn = null;
                }
            }
            long finish = System.currentTimeMillis();
            System.out.println("Finished at:" + finish);
            System.out.println("Finished in : " + ((finish - start)/ 1000) + " Secs");
            inStream.close();
            socket.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client("127.0.0.1", 6000);
    }

}
