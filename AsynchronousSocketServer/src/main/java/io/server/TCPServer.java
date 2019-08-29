package io.server;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer extends AbstractServer<ObjectOutputStream> {

    private final int port;

    private ServerSocket server;
    private Socket socket;

    public TCPServer(int port){
        this.port = port;
    }

    @Override
    protected ObjectOutputStream start() throws IOException {
        server = new ServerSocket(port);
        socket = server.accept();
        return new ObjectOutputStream(socket.getOutputStream());
    }

    @Override
    protected void terminate(ObjectOutputStream outputStream) throws IOException {
        outputStream.close();
        socket.close();
        server.close();
    }

    @Override
    protected void write(Serializable i, ObjectOutputStream outputStream) throws IOException {
        outputStream.writeObject(i);
    }
}
