package xptObj.tcp;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import objects.Lineitem;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    private Client(String address, int port) {
        Socket socket;
        String line = "";

        Kryo kryo = new Kryo();
        registerKryoClasses(kryo);
        try {
            socket = new Socket(address, port);
            System.out.println("Connected With Spark Client");
            Input input = new Input(socket.getInputStream());
            System.out.println("Waiting");
            long start = System.currentTimeMillis();
            int count = 0;
            ArrayList responseItems = kryo.readObject(input, ArrayList.class);
            System.out.println(responseItems.size());
            for(int i = 0; i<responseItems.size(); i++){
                responseItems.get(i);
            }
//            while (line != null) {
//                try {
//                    Lineitem x = kryo.readObject(input, Lineitem.class);
////                    System.out.println(x.toString());
//                    count++;
//                } catch (Exception e) {
//                    line = null;
//                }
//            }
//            System.out.println(count);
            long finish = System.currentTimeMillis();
            System.out.println("Finished an : " + finish + " Secs");
            System.out.println("Finished in : " + ((finish - start)/ 1000) + " Secs");
            socket.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void registerKryoClasses(Kryo kryo) {
        kryo.register(ArrayList.class);
        kryo.register(Lineitem.class);
    }
    public static void main(String[] args) {
        new Client("127.0.0.1", 6000);
    }
}