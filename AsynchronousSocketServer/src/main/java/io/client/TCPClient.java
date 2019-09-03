package io.client;

import objects.Lineitem;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;

public class TCPClient extends AbstractClient<ObjectInputStream>{

    private final String address;
    private final int port;

    private Socket socket;

    public TCPClient(String address, int port){
        this.address = address;
        this.port = port;
    }

    @Override
    protected ObjectInputStream start() throws IOException {
        socket = new Socket(address, port);
        return new ObjectInputStream(socket.getInputStream());
    }

    @Override
    protected void terminate(ObjectInputStream inputStream) throws IOException {
        inputStream.close();
        socket.close();
    }

    @Override
    protected Serializable read(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
//        return (Serializable) inputStream.readObject();
        return (Serializable) Lineitem.fromBytes((byte[])inputStream.readObject());
    }
}
