package io;

import io.server.*;
import objects.Lineitem;

public class TestServer {

    public static void main(String[] args) {
        String input = args[0];
        AbstractServer server;
        switch(args[1]){
            case "udp":
                server = new UDPServer(6000);
                break;
            case "tcp":
                server = new TCPServer(6000);
                break;
            case "kryo":
                server = new KyroServer(6000, Lineitem.class);
                break;
            case "pipe":
                server = new NamedPipeServer(args[2]);
                break;
            default:
                server = null;
                System.out.println("Please Select a valid protocol");
                System.exit(0);
        }
        server.run(input);
    }

}
