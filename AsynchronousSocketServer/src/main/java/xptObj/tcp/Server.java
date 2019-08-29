package xptObj.tcp;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import objects.Lineitem;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private Server(int port, String inputPath) {
        DataInputStream rawData;
        Output outStream;
        List<Lineitem> lineitems = new ArrayList<>();
        Kryo kryo = new Kryo();
        registerKryoClasses(kryo);

        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Spark Server started");
            System.out.println("Waiting for Incorta Client ...");

            try (Socket socket = server.accept()){
                outStream =  new Output(socket.getOutputStream());
                System.out.println("Connected with Incorta Client");

                rawData = new DataInputStream(new FileInputStream(inputPath));
                registerLineItems(lineitems, rawData);
                Thread.sleep(10000);

                long start = System.currentTimeMillis();

                lineitems.forEach(object -> {
                    try {
                        kryo.writeObject(outStream, object);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                long finish = System.currentTimeMillis();
                System.out.println("Started at : " + start + " Secs");
                System.out.println("Finished in : " + ((finish - start)/ 1000) + " Secs");
                rawData.close();
                outStream.close();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void registerLineItems(List<Lineitem> lineItems, DataInputStream rawData) throws IOException {
        System.out.println("Starting Serializing Data");
        String newLine = rawData.readLine();

        while (newLine != null){
            String[] items = newLine.split("\\|");

            lineItems.add(new Lineitem(Long.parseLong(items[0]), Long.parseLong(items[1]), Long.parseLong(items[2]), Long.parseLong(items[3]),
                    Double.parseDouble(items[4]), Double.parseDouble(items[5]), Double.parseDouble(items[6]), Double.parseDouble(items[7]),
                    items[8], items[9], items[10], items[11], items[12], items[13], items[14], items[15]));
            newLine = rawData.readLine();
        }
        System.out.println("Finished Serializing Data");
    }

    private void registerKryoClasses(Kryo kryo) {
        kryo.register(ArrayList.class);
        kryo.register(Lineitem.class);
    }

    public static void main(String[] args) {
        new Server(6000, "/home/yousry/dummy_db/tpch/1/tbl2/lineitem.150k.1");
    }
}