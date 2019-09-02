package io.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class KryoNamedPipeServer extends AbstractServer<Output> {
    private final String address;
    private Output pipe;
    private final Kryo kryo;
    private final Class clazz;

    public KryoNamedPipeServer(String address, Class clazz) {
        this.address = address;
        this.clazz = clazz;

        this.kryo = new Kryo();
        kryo.register(clazz);
    }

    @Override
    protected Output start() throws IOException {
        pipe = new Output(new FileOutputStream(address));
        return pipe;
    }

    @Override
    protected void terminate(Output outputStream) throws IOException {
        pipe.close();
    }

    @Override
    protected void write(Serializable i, Output outputStream) throws IOException {
        kryo.writeObject(outputStream, i);
    }
}
