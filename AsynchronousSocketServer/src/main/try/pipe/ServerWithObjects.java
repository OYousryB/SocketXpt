package pipe;

import objects.Lineitem;
import objects.Nation;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ServerWithObjects {
    public static void main(String[] args) {
        try {
            DataInputStream rawData = new DataInputStream(new FileInputStream("/home/yousry/dummy_db/tpch/1/tbl/lineitem/lineitem.tbl.1"));
            ObjectOutputStream pipe = new ObjectOutputStream(new FileOutputStream("/home/yousry/mypipe"));
            String echoText = rawData.readLine();
            List<Lineitem> lineitems = new ArrayList<>();
            List<Nation> nations = new ArrayList<>();
            String newLine = rawData.readLine();

            while (newLine != null){
                String[] items = newLine.split("\\|");
//                nations.add(new Nation(Long.parseLong(items[0]), items[1], Long.parseLong(items[2]),items[3] ));

                lineitems.add(new Lineitem(Long.parseLong(items[0]), Long.parseLong(items[1]), Long.parseLong(items[2]), Long.parseLong(items[3]),
                        Double.parseDouble(items[4]), Double.parseDouble(items[5]), Double.parseDouble(items[6]), Double.parseDouble(items[7]),
                        items[8], items[9], items[10], items[11], items[12], items[13], items[14], items[15]));
                newLine = rawData.readLine();
            }
            System.out.println("Finished Serializing Data");

            long start = System.currentTimeMillis();
            lineitems.forEach(object -> {
                try {
                    pipe.writeObject(object);
//                    Thread.sleep(1000);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            long finish = System.currentTimeMillis();
            System.out.println("Finished in : " + ((finish - start)/ 1000) + " Secs");
            pipe.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
