package xptObj.tcp;

import objects.Lineitem;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private Server(int port) {
        DataInputStream rawData;
        ObjectOutputStream outStream;
        List<Lineitem> lineitems = new ArrayList<>();
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Spark Server started");
            System.out.println("Waiting for Incorta Client ...");

            Socket socket = server.accept();
            outStream = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Connected with Incorta Client");

            rawData = new DataInputStream(new FileInputStream("/home/yousry/dummy_db/tpch/10/tbl/lineitem/lineitem.txt"));
            String newLine = rawData.readLine();
            while (newLine != null){
                String[] items = newLine.split("\\|");
                lineitems.add(new Lineitem(Long.parseLong(items[0]), Long.parseLong(items[1]), Long.parseLong(items[2]), Long.parseLong(items[3]),
                        Double.parseDouble(items[4]), Double.parseDouble(items[5]), Double.parseDouble(items[6]), Double.parseDouble(items[7]),
                        items[8], items[9], items[10], items[11], items[12], items[13], items[14], items[15]));
                newLine = rawData.readLine();
            }
            System.out.println("Finished Serializing Data");

            Thread.sleep(10000);
            ObjectOutputStream finalOutStream = outStream;
            long start = System.currentTimeMillis();
            lineitems.forEach(object -> {
                try {
                    finalOutStream.writeObject(object);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            long finish = System.currentTimeMillis();
            System.out.println("Finished in : " + ((finish - start)/ 1000) + " Secs");
            rawData.close();
            outStream.close();
            socket.close();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Server(6000);
    }

}

