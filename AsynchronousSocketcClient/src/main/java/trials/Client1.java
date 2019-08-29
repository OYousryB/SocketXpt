package trials;

import java.net.*;
import java.io.*;

public class Client1 {

    // constructor to put ip address and port
    private Client1(String address, int port) {
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

            input = new DataInputStream(new FileInputStream("/home/yousry/dummy_db/tpch/1/tbl/lineitem/lineitem.tbl.1"));


        } catch(IOException i) {
            System.out.println(i);
        }

        File path = new File("/home/yousry/dummy_db/tpch/1/tbl2");
        File [] files = path.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                try {
                    input = new DataInputStream(new FileInputStream(file));
                    String line = "";

                    while (!line.equals("Over")) {
                        try {
                            line = input.readLine();
                            out.writeUTF(line);
                            in.readUTF();
//                            Thread.sleep(1000);
                        } catch (Exception e) {
                            line = "Over";
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
        // string to read message from input

        // keep reading until "Over" is input


        // close the connection
        try {
            input.close();
            out.close();
            socket.close();
        } catch(IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        Client1 client = new Client1("127.0.0.1", 6000);
    }
}