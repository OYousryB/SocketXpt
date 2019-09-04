package xpt.pipes;

import java.io.RandomAccessFile;

public class Client {
    public static void main(String[] args) {
        try {
            String pipeOut = "";
            RandomAccessFile pipe = new RandomAccessFile("/home/yousry/mypipe", "r");
            long start = System.currentTimeMillis();

            while(pipeOut != null){
                pipeOut = pipe.readLine();
//                System.out.println(pipeOut);
            }
            long finish = System.currentTimeMillis();
            System.out.println("Finished at:" + finish);
            System.out.println("Finished in : " + ((finish - start)/ 1000) + " Secs");
            pipe.close();
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
