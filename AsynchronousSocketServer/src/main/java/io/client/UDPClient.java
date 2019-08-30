package io.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPClient extends AbstractClient<ObjectInputStream> {
    private final int port;

    private DatagramSocket dataSocket;
    private DatagramPacket dataReceive;
    private byte[] receive = new byte[95535];

    public UDPClient(int port) {
        this.port = port;
    }

    @Override
    protected ObjectInputStream start() throws IOException {
        dataSocket = new DatagramSocket(port);

        dataReceive = new DatagramPacket(receive, receive.length);
        dataSocket.receive(dataReceive);
        byte[] data = dataReceive.getData();
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        return new ObjectInputStream(in);
    }

    @Override
    protected void terminate(ObjectInputStream inputStream) throws IOException {
        dataSocket.close();
    }

    @Override
    protected Serializable read(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        return (Serializable) inputStream.readObject();
    }
}
