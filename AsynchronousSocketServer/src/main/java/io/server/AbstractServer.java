package io.server;

import io.util.Profiler;
import objects.Lineitem;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractServer<O extends OutputStream> {

    private List<Serializable> read(String inputPath) throws IOException {

        List<Serializable> data = new LinkedList<>();
        DataInputStream rawData = new DataInputStream(new FileInputStream(inputPath));
        long start = Profiler.startProfile("Starting Loading Data");

        String newLine = rawData.readLine();
        while (newLine != null){
            String[] items = newLine.split("\\|");

            data.add(new Lineitem(Long.parseLong(items[0]), Long.parseLong(items[1]), Long.parseLong(items[2]), Long.parseLong(items[3]),
                    Double.parseDouble(items[4]), Double.parseDouble(items[5]), Double.parseDouble(items[6]), Double.parseDouble(items[7]),
                    items[8], items[9], items[10], items[11], items[12], items[13], items[14], items[15]));
            newLine = rawData.readLine();
        }

        Profiler.endProfile("Finished Loading Data", start);

        return data;
    }

    public void run(String inputPath){
        int count = 0;
        try {
            List<Serializable> data = read(inputPath);
            O outputStream = start();
            long start = Profiler.startProfile("Starting Transmitting Data");
            for(Serializable i: data) {
                write(i, outputStream);
                count++;
            }
            Profiler.endProfile("Finished Transmitting Data", start);
            Profiler.transmitted(count);
            terminate(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract O start() throws IOException;

    protected abstract void terminate(O outputStream) throws IOException;

    protected abstract void write(Serializable i, O outputStream) throws IOException;

}
