package xptObj.pipe;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import objects.Lineitem;

import java.io.FileInputStream;
import java.util.ArrayList;

public class Client {
    private Client(String pipePath) {
        try (Input input = new Input(new FileInputStream(pipePath))){

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
