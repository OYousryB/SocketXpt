package objects;

public class GenericTable {
    private final String name;
    private final char separator;
    private final String path;

    private Row[] rows;
    private int columnsCount;
    private int[] columnsTypes;

    public GenericTable(String name, char separator, String path) {
        this.name = name;
        this.separator = separator;
        this.path = path;
    }
}
