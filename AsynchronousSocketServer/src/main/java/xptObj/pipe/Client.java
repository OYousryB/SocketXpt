package xptObj.pipe;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import helper.InstrumentationAgent;
import objects.Lineitem;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    private Client(String pipePath) {
//        try (ObjectInputStream pipe = new ObjectInputStream(new FileInputStream(pipePath))) {
        try (Input input = new Input(new FileInputStream(pipePath))){
            String echoResponse = "";
            ArrayList<Lineitem> woi = new ArrayList<>();

            Kryo kryo = new Kryo();
            registerKryoClasses(kryo);

            long start = System.currentTimeMillis();

            ArrayList responseItems = kryo.readObject(input, ArrayList.class);
            System.out.println(responseItems.size());

            for(int i = 0; i<responseItems.size(); i++){
                responseItems.get(i);
            }
            long finish = System.currentTimeMillis();

            System.out.println("Finished an : " + finish + " Secs");
            System.out.println("Finished in : " + ((finish - start)/ 1000) + " Secs");
//            while(echoResponse != null){
//                try {
//                    Object obo = pipe.readObject();
//                    System.out.println("pog");
//                    woi = (ArrayList<Lineitem>)obo;
//                    System.out.println(pipe.readObject());
//                } catch (Exception e){
//                    echoResponse = null;
//                }
//            }
//            pipe.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void registerKryoClasses(Kryo kryo) {
        kryo.register(ArrayList.class);
        kryo.register(Lineitem.class);
    }
    public static void main(String[] args) {
        new Client("/home/yousry/mypipe");
    }
}
