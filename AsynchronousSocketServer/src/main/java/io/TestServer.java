package io;

import io.server.AbstractServer;
import io.server.KyroServer;
import io.server.TCPServer;
import objects.Lineitem;

public class TestServer {

    public static void main(String[] args) {
        String input = "/home/msaad/TPCH/tpch-data/1g/lineitem.150k";
//        AbstractServer server = new TCPServer(6000);
        AbstractServer server = new KyroServer(6000, Lineitem.class);
        server.run(input);
    }

}
