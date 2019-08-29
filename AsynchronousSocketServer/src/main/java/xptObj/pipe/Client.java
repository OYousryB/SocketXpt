package xptObj.pipe;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import helper.InstrumentationAgent;
import objects.Lineitem;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    private Client() {
        try {
//            ObjectInputStream pipe = new ObjectInputStream(new FileInputStream("/home/yousry/mypipe"));
            Input input = new Input(new FileInputStream("/home/yousry/mypipe"));

            Kryo kryo = new Kryo();
            kryo.register(ArrayList.class);
            kryo.register(Lineitem.class);


            String echoResponse = "";


            ArrayList<Lineitem> woi = new ArrayList<>();
//            Object obo = pipe.readObject();

            System.out.println("pog");

            long start = System.currentTimeMillis();
            ArrayList object2 = kryo.readObject(input, ArrayList.class);
            input.close();

//            woi = (ArrayList<Lineitem>)obo;

            System.out.println(object2.size());
            for(int i=0;i<object2.size();i++){
                object2.get(i);
            }
            long finish = System.currentTimeMillis();

            System.out.println("Finished an : " + finish + " Secs");
            System.out.println("Finished in : " + ((finish - start)/ 1000) + " Secs");
//            while(echoResponse != null){
//                try {
//
//                    Object obo = pipe.readObject();
//                    System.out.println("pog");
//                    woi = (ArrayList<Lineitem>)obo;
//                    System.out.println(pipe.readObject());
//
//                } catch (Exception e){
//                    echoResponse = null;
//                }
//            }
//            pipe.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}
