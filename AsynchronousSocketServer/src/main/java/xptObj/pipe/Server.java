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
    private Server() {
        Kryo kryo = new Kryo();
        kryo.register(ArrayList.class);
        kryo.register(Lineitem.class);

        try {
            DataInputStream rawData = new DataInputStream(new FileInputStream("/home/yousry/dummy_db/tpch/1/tbl/lineitem/lineitem.tbl.1"));
//            ObjectOutputStream pipe = new ObjectOutputStream(new FileOutputStream("/home/yousry/mypipe"));
            Output output = new Output(new FileOutputStream("/home/yousry/mypipe"));


            List<Lineitem> lineitems = new ArrayList<>();
            String newLine = rawData.readLine();
            System.out.println("Starting Serializing Data");

            while (newLine != null){
                String[] items = newLine.split("\\|");

                lineitems.add(new Lineitem(Long.parseLong(items[0]), Long.parseLong(items[1]), Long.parseLong(items[2]), Long.parseLong(items[3]),
                        Double.parseDouble(items[4]), Double.parseDouble(items[5]), Double.parseDouble(items[6]), Double.parseDouble(items[7]),
                        items[8], items[9], items[10], items[11], items[12], items[13], items[14], items[15]));
                newLine = rawData.readLine();
            }

            System.out.println(lineitems.size());
            System.out.println("Finished Serializing Data");
            long start = System.currentTimeMillis();

            kryo.writeObject(output, lineitems);
            long finish = System.currentTimeMillis();

//            pipe.writeObject(lineitems.get(1));
            output.close();

            System.out.println("Started at : " + start + " Secs");
            System.out.println("Finished in : " + ((finish - start)/ 1000) + " Secs");
//            pipe.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server();
    }

}

