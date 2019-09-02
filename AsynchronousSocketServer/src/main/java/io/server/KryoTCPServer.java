package io.server;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

public class KryoTCPServer extends AbstractServer<Output> {

    private final Kryo kryo;
    private final int port;

    private ServerSocket server;
    private Socket socket;

    public KryoTCPServer(int port, Class clazz){
        this.kryo = new Kryo();
        kryo.register(clazz);
        this.port = port;
    }

    @Override
    protected Output start() throws IOException {
        server = new ServerSocket(port);
        socket = server.accept();
        return new Output(socket.getOutputStream());
    }

    @Override
    protected void terminate(Output outputStream) throws IOException {
        outputStream.close();
        socket.close();
        server.close();
    }

    @Override
    protected void write(Serializable i, Output outputStream) throws IOException {
        kryo.writeObject(outputStream, i);
    }
}
