package trials;

import java.io.*;
import java.net.Socket;

public class CustClient1 {

    // constructor to put ip address and port
    private CustClient1(String address, int port) {
        DataOutputStream out = null;
        DataInputStream input = null;
        DataInputStream in = null;

        // initialize socket and input output streams
        Socket socket = null;
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());

            input = new DataInputStream(new FileInputStream("/home/yousry/dummy_db/tpch/1/tbl2/nation.tbl"));
        } catch(IOException i) {
            System.out.println(i);
        }

        String line = "";

        while (!line.equals("Over")) {
            try {
                line = input.readLine();
                out.writeUTF(line);
                in.readUTF();
//              Thread.sleep(1000);
            } catch (Exception e) {
                line = "Over";
            }
        }

        try {
            input.close();
            out.close();
            socket.close();
        } catch(IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        CustClient1 client = new CustClient1("127.0.0.1", 6000);
    }
}