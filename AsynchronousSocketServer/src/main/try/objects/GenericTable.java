package objects;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GenericTable {
    private final String name;
    private final String separator;
    private final String path;

    private Row[] rows;
    private int columnsCount;
    private int[] columnsTypes;

    private DataInputStream rawData;

    public GenericTable(String name, String separator, String path) throws FileNotFoundException {
        this.name = name;
        this.separator = separator;
        this.path = path;
        this.rawData = new DataInputStream(new FileInputStream(this.path));
    }

    public void initRawData() {
    }

    public int getColumnsMetadata(){
        return 0;
    }

    public void addRows(){
    }
}
