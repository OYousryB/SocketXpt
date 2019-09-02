package io.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.*;


public class KryoUDPServer extends AbstractServer<Output>{
    private final int port;
    private final Kryo kryo;

    private DatagramSocket dataSocket;
    private InetAddress IPAddress;


    public KryoUDPServer(int port, Class clazz) {
        this.port = port;

        this.kryo = new Kryo();
        kryo.register(clazz);
    }

    @Override
    protected Output start() throws IOException {
        IPAddress = InetAddress.getLocalHost();
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(999999);
        dataSocket = new DatagramSocket();
        return new Output(byteStream);
    }

    @Override
    protected void terminate(Output outputStream) throws IOException {
        dataSocket.close();
    }

    @Override
    protected void write(Serializable i, Output outputStream) throws IOException {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        Output os = new Output(byteOutputStream);
        kryo.writeObject(os, i);

        byte[] data = byteOutputStream.toByteArray();

        DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, port);
        dataSocket.send(sendPacket);
    }
}
