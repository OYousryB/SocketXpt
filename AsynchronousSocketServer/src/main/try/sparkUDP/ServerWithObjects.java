package sparkUDP;

import objects.Lineitem;
import objects.Nation;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerWithObjects {
    private ServerWithObjects(int port) {
        DatagramSocket ds;
        String line = "";
        DataInputStream rawData = null;
        List<Lineitem> lineitems = new ArrayList<>();
        List<Nation> nations = new ArrayList<>();

        try {
            rawData = new DataInputStream(new FileInputStream("/home/yousry/dummy_db/tpch/1/tbl/lineitem/lineitem.tbl.2"));
            String newLine = rawData.readLine();
            while (newLine != null){
                String[] items = newLine.split("\\|");
//                nations.add(new Nation(Long.parseLong(items[0]), items[1], Long.parseLong(items[2]), items[3]));

                lineitems.add(new Lineitem(Long.parseLong(items[0]), Long.parseLong(items[1]), Long.parseLong(items[2]), Long.parseLong(items[3]),
                        Double.parseDouble(items[4]), Double.parseDouble(items[5]), Double.parseDouble(items[6]), Double.parseDouble(items[7]),
                        items[8], items[9], items[10], items[11], items[12], items[13], items[14], items[15]));
                newLine = rawData.readLine();
            }
            System.out.println("Finished Serializing Data");
            System.out.println(lineitems.size());
            ds = new DatagramSocket();
            InetAddress ip = InetAddress.getLocalHost();
            byte[] sender;

            Thread.sleep(10000);
            AtomicInteger count = new AtomicInteger();
            long start = System.currentTimeMillis();
            lineitems.forEach(object -> {
                try {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    ObjectOutputStream os = new ObjectOutputStream(outputStream);
                    os.writeObject(object);
                    byte[] data = outputStream.toByteArray();
                    DatagramPacket sendPacket = new DatagramPacket(data, data.length, ip, port);
                    ds.send(sendPacket);
                    Thread.sleep(10000);
//                    System.out.println("packet " + count.getAndIncrement() +" Sent");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            while (!line.equals("Over")) {
                try {
                    line = rawData.readLine();
                    sender = line.getBytes();
                    Thread.sleep(1000);

                    DatagramPacket DpSend = new DatagramPacket(sender, sender.length, ip, port);
                    ds.send(DpSend);
                } catch (Exception e) {
                    line = "Over";
                }
            }
            long finish = System.currentTimeMillis();
            System.out.println("Finished in : " + ((finish - start)/ 1000) + " Secs");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new ServerWithObjects(1234);
    }
}