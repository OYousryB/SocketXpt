package io.server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class NamedPipeServer extends AbstractServer<ObjectOutputStream> {
    private final String address;
    private ObjectOutputStream pipe;

    public NamedPipeServer(String address) {
        this.address = address;
    }

    @Override
    protected ObjectOutputStream start() throws IOException {
        pipe = new ObjectOutputStream(new FileOutputStream(address));
        return pipe;
    }

    @Override
    protected void terminate(ObjectOutputStream outputStream) throws IOException {
        pipe.close();
    }

    @Override
    protected void write(Serializable i, ObjectOutputStream outputStream) throws IOException {
        pipe.writeObject(i);
    }
}
