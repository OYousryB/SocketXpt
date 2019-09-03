package io.client;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import objects.Lineitem;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

public class KryoTCPClient extends AbstractClient<Input>{

    private final Kryo kryo;
    private final String address;
    private final int port;
    private final Class clazz;

    private Socket socket;

    public KryoTCPClient(String address, int port, Class clazz){
        this.kryo = new Kryo();
        kryo.register(clazz);
        this.address = address;
        this.port = port;
        this.clazz = clazz;
    }

    @Override
    protected Input start() throws IOException {
        socket = new Socket(address, port);
        return new Input(socket.getInputStream());
    }

    @Override
    protected void terminate(Input inputStream) throws IOException {
        inputStream.close();
        socket.close();
    }

    @Override
    protected Serializable read(Input inputStream) throws IOException, ClassNotFoundException {
//        return (Serializable) kryo.readObject(inputStream, clazz);
        return (Serializable) Lineitem.fromBytes((byte[])kryo.readObject(inputStream, clazz));

    }
}
