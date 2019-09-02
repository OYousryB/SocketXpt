package io.client;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class KryoUDPClient extends AbstractClient<Input> {
    private final int port;
    private final Kryo kryo;
    private final Class clazz;

    private DatagramSocket dataSocket;
    private DatagramPacket dataReceive;
    private byte[] receive = new byte[95535];

    public KryoUDPClient(int port, Class clazz) {
        this.port = port;
        this.clazz = clazz;

        this.kryo = new Kryo();
        kryo.register(clazz);
    }

    @Override
    protected Input start() throws IOException {
        dataSocket = new DatagramSocket(port);

        dataReceive = new DatagramPacket(receive, receive.length);
        dataSocket.receive(dataReceive);
        byte[] data = dataReceive.getData();
        return new Input(new ByteArrayInputStream(data));
    }

    @Override
    protected void terminate(Input inputStream) throws IOException {
        dataSocket.close();
    }

    @Override
    protected Serializable read(Input inputStream) throws IOException, ClassNotFoundException {
        dataReceive = new DatagramPacket(receive, receive.length);
        dataSocket.setSoTimeout(50);
        dataSocket.receive(dataReceive);
        byte[] data = dataReceive.getData();
        inputStream = new Input(new ByteArrayInputStream(data));

        return (Serializable) inputStream.read();
    }
}
