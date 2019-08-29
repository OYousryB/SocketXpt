package io.client;

import io.util.Profiler;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public abstract class AbstractClient<O extends InputStream> {

    protected abstract O start() throws IOException;

    protected abstract void terminate(O inputStream) throws IOException;

    protected abstract Serializable read(O inputStream) throws IOException, ClassNotFoundException;

    public void run(){
        try {
            O inputStream = start();
            long start = Profiler.startProfile("Starting Receiving Data");
            Serializable d = null;
            int count = 0;
            do {
                try {
                    d = read(inputStream);
                    count++;
                } catch (Exception e) {
                    System.out.println("EOF");
                    break;
                }
            } while(d != null);
            Profiler.endProfile("Finished Receiving " + count + " objects", start);
            terminate(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
