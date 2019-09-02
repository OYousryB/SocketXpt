package io;

import io.client.*;
import objects.Lineitem;

public class TestClient {

    public static void main(String[] args) {
        AbstractClient client;

        switch(args[0]){
            case "udp":
                client = new UDPClient(6000);
                break;
            case "tcp":
                client = new TCPClient("localhost", 6000);
                break;
            case "pipe":
                client  = new NamedPipeClient(args[1]);
                break;
            case "kryo_tcp":
                client = new KryoTCPClient("localhost", 6000, Lineitem.class);
                break;
            case "kryo_udp":
                client = new KryoUDPClient(6000, Lineitem.class);
                break;
            case "kryo_pipe":
                client = new KryoNamedPipeClient(args[1], Lineitem.class);
                break;
            default:
                client = null;
                System.out.println("Please Select a valid protocol");
                System.exit(0);
        }
        client.run();
    }
}
