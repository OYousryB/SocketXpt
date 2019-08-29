package Dummy;

import objects.Lineitem;
import objects.Nation;

import javax.sound.sampled.Line;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientWithObjects {

    private ClientWithObjects(String address, int port) {
        ObjectInputStream in = null;
        int count = 0;
        Socket socket;
        try {
            socket = new Socket(address, port);
            System.out.println("Connected to incorta client");

            in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch(IOException i) {
            System.out.println(i);
        }

        while (true) {
            try {
                Lineitem n = (Lineitem) in.readObject();
//                System.out.println("Received " + n.getN_name() + " Object(s)");
                System.out.println("Count is:" + count++);
            }
            catch(Exception e) {
                System.out.println(e);
            }
        }
    }

    public static void main(String[] args) {
        new ClientWithObjects("127.0.0.1", 7000);
    }
}