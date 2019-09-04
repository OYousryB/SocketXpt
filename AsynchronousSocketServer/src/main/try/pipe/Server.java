package pipe;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            DataInputStream rawData = new DataInputStream(new FileInputStream("/home/yousry/dummy_db/tpch/1/tbl/lineitem/lineitem.tbl.1"));
            RandomAccessFile pipe = new RandomAccessFile("/home/yousry/mypipe", "rw");
            String echoText = rawData.readLine();

            long start = System.currentTimeMillis();
            while (echoText != null) {
                try {
                    echoText = rawData.readLine();
                    String res = echoText + "\n";
                    pipe.write ( res.getBytes() );
//                    Thread.sleep(5000);
                } catch (Exception e) {
                    echoText = null;
                }
            }
            long finish = System.currentTimeMillis();
            System.out.println("Finished in : " + ((finish - start)/ 1000) + " Secs");
            pipe.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
