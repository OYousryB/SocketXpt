package io.server;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class UDPServer extends AbstractServer<ObjectOutputStream>{
    private final int port;

    private DatagramSocket dataSocket;
    private InetAddress IPAddress;

    public UDPServer(int port) {
        this.port = port;
    }

    @Override
    protected ObjectOutputStream start() throws IOException {
        IPAddress = InetAddress.getLocalHost();
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(95535);
        dataSocket = new DatagramSocket();
        return new ObjectOutputStream(byteStream);
    }

    @Override
    protected void terminate(ObjectOutputStream outputStream) throws IOException {
        dataSocket.close();
    }

    @Override
    protected void write(Serializable i, ObjectOutputStream outputStream) throws IOException {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(byteOutputStream);
        os.writeObject(i);
        byte[] data = byteOutputStream.toByteArray();

        DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, port);
        dataSocket.send(sendPacket);
    }
}
