package io.client;

import com.esotericsoftware.kryo.io.Input;

import java.io.*;

public class NamedPipeClient extends AbstractClient<ObjectInputStream> {
    private final String address;
    private ObjectInputStream pipe;

    public NamedPipeClient(String address) {
        this.address = address;
    }

    @Override
    protected ObjectInputStream start() throws IOException {
        pipe = new ObjectInputStream(new FileInputStream(address));
        return pipe;
    }

    @Override
    protected void terminate(ObjectInputStream inputStream) throws IOException {
        pipe.close();
    }

    @Override
    protected Serializable read(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        return (Serializable) pipe.readObject();
    }
}
