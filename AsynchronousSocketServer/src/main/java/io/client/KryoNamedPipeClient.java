package io.client;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class KryoNamedPipeClient extends AbstractClient<Input> {
    private final String address;
    private Input pipe;
    private final Kryo kryo;
    private final Class clazz;

    public KryoNamedPipeClient(String address, Class clazz) {
        this.address = address;
        this.kryo = new Kryo();
        kryo.register(clazz);
        this.clazz = clazz;
    }

    @Override
    protected Input start() throws IOException {
        pipe = new Input(new FileInputStream(address));
        return pipe;
    }

    @Override
    protected void terminate(Input inputStream) throws IOException {
        pipe.close();
    }

    @Override
    protected Serializable read(Input inputStream) throws IOException, ClassNotFoundException {
        return (Serializable) kryo.readObject(inputStream, clazz);
    }
}
