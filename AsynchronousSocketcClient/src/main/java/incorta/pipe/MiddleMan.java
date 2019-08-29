package incorta.pipe;

import java.io.DataOutputStream;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;

public class MiddleMan {
    private MiddleMan(int port){
        DataOutputStream out;
        String echoResponse = "";

        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Incorta Server started");
            System.out.println("Waiting for Actual Client ...");
            Socket clientSocket = server.accept();
            System.out.println("Connected!");

            out = new DataOutputStream(clientSocket.getOutputStream());
            RandomAccessFile pipe = new RandomAccessFile("/home/yousry/mypipe", "r");
            while(echoResponse != null){
                echoResponse = pipe.readLine();
                out.writeUTF(echoResponse);
            }
            pipe.close();

        } catch (Exception ignored) {
        }
    }
    public static void main(String[] args) {
        new MiddleMan(7000);
    }


}
