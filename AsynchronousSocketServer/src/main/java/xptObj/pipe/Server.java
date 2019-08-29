package xptObj.pipe;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import helper.InstrumentationAgent;
import objects.Lineitem;
import objects.Nation;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private Server(String inputPath, String pipePath) {
        Kryo kryo = new Kryo();
        registerKryoClasses(kryo);

        try (DataInputStream rawData = new DataInputStream(new FileInputStream(inputPath))){
//            try (ObjectOutputStream pipe = new ObjectOutputStream(new FileOutputStream(pipePath))) {
            try (Output output = new Output(new FileOutputStream(pipePath))){
                List<Lineitem> lineItems = new ArrayList<>();
                registerLineItems(lineItems, rawData);

                long start = System.currentTimeMillis();
                kryo.writeObject(output, lineItems);
//            pipe.writeObject(lineItems);
                long finish = System.currentTimeMillis();

                System.out.println("Started at : " + start + " Secs");
                System.out.println("Finished in : " + ((finish - start)/ 1000) + " Secs");
            }
        } catch (Exception e) {
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
        new Server("/home/yousry/dummy_db/tpch/1/tbl/lineitem/lineitem.tbl.1","/home/yousry/mypipe");
    }
}

