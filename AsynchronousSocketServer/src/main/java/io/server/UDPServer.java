package io.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UDPServer extends AbstractServer<ObjectOutputStream>{
    private final int port;

    private DatagramSocket dataSocket;
    private InetAddress ip;

    {
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public UDPServer(int port) {
        this.port = port;
    }

    @Override
    protected ObjectOutputStream start() throws IOException {
        dataSocket = new DatagramSocket();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        return new ObjectOutputStream(outputStream);
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

        DatagramPacket sendPacket = new DatagramPacket(data, data.length, ip, port);
        dataSocket.send(sendPacket);
    }
}
