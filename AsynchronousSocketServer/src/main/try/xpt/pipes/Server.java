package xpt.pipes;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.RandomAccessFile;

public class Server {
    public static void main(String[] args) {
        try {
            System.out.println("Init new Named pipe stream");
            DataInputStream inputData = new DataInputStream(new FileInputStream("/home/yousry/dummy_db/tpch/1/tbl/lineitem/lineitem.tbl.1"));
            RandomAccessFile pipe = new RandomAccessFile("/home/yousry/mypipe", "rw");
            String pipeIn = inputData.readLine();

            long start = System.currentTimeMillis();
            while(pipeIn != null){
                String pipedLine =  pipeIn + "\n";
                pipeIn = inputData.readLine();
                pipe.write(pipedLine.getBytes());
//                Thread.sleep(1000);
            }
            long finish = System.currentTimeMillis();
            System.out.println("Started at:" + start);
            System.out.println("Finished in : " + ((finish - start)/ 1000) + " Secs");
            pipe.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
