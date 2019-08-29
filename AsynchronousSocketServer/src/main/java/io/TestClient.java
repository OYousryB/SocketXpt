package io;

import io.client.AbstractClient;
import io.client.KyroClient;
import io.client.TCPClient;
import objects.Lineitem;

public class TestClient {

    public static void main(String[] args) {
//        AbstractClient client = new TCPClient("localhost", 6000);
        AbstractClient client = new KyroClient("localhost", 6000, Lineitem.class);
        client.run();
    }
}
