package jdbc;

import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class RemoteResultSetWithSequentialColumnAccess extends ResultSetAdapter {

    private BlockingQueue<byte[]> blockingQueue = new LinkedBlockingDeque<>();
    private boolean endOfData = false;
    private ByteBuffer current;
    private int lastReadColumnIndex;
    private int lastStringSize;
    private byte[] nulls;
    private final byte[] types;

    public static final byte BOOLEAN_TYPE     = 1;
    public static final byte TIMESTAMP_TYPE   = 2;
    public static final byte SHORT_TYPE       = 3;
    public static final byte INT_TYPE         = 4;
    public static final byte LONG_TYPE        = 5;
    public static final byte DOUBLE_TYPE      = 6;
    public static final byte STRING_TYPE      = 7;

    public RemoteResultSetWithSequentialColumnAccess(byte[] types){
        this.types = types;
    }

    public void addRow(byte[] data) {
        blockingQueue.add(data);
    }

    public void setEndOfData(boolean state) {
        endOfData = state;
    }
    @Override
    public boolean next() throws SQLException {
        if(endOfData && blockingQueue.isEmpty())
            return false;
        byte[] data = blockingQueue.poll();
        current = ByteBuffer.wrap(data);
        int columnCount = types.length;
        int bytesCount = columnCount >> 3;
        if((columnCount & 7) > 0)
            bytesCount ++;
        nulls = new byte[bytesCount];
        current.get(nulls);
        lastReadColumnIndex = 0;
        return true;
    }

    private boolean isNull(int columnIndex) {
        int byteOffset = (columnIndex - 1) >> 3;
        int bitIndex = (columnIndex - 1) & 7;
        return ((nulls[byteOffset] >> (7 - bitIndex)) & 1) != 0;
    }

    @Override
    public Object getObject(int columnIndex) throws SQLException {
        Object o;
        byte columnType = types[columnIndex - 1];
        switch(columnType){
            case BOOLEAN_TYPE: o = getBoolean(columnIndex); break;
            case TIMESTAMP_TYPE: o = getTimestamp(columnIndex); break;
            case SHORT_TYPE: o = getShort(columnIndex); break;
            case INT_TYPE: o = getInt(columnIndex); break;
            case LONG_TYPE: o = getLong(columnIndex); break;
            case DOUBLE_TYPE: o = getDouble(columnIndex); break;
            case STRING_TYPE: o = getString(columnIndex); break;
            default:
                throw new RuntimeException("Unsupported Type " + columnType);
        }
        return isNull(columnIndex) ? null : o;
    }

    @Override
    public boolean getBoolean(int columnIndex) throws SQLException {
        if(columnIndex == lastReadColumnIndex) {
            current.position(current.position() - 1);
        } else {
            assert (columnIndex == lastReadColumnIndex+1);
        }
        lastReadColumnIndex = columnIndex;
        byte v = current.get();
        return (v > 0);
    }

    @Override
    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        if(columnIndex == lastReadColumnIndex) {
            current.position(current.position() - 8);
        } else {
            assert (columnIndex == lastReadColumnIndex+1);
        }
        lastReadColumnIndex = columnIndex;
        long v = current.getLong();
        return new Timestamp(v);
    }

    @Override
    public short getShort(int columnIndex) throws SQLException {
        if(columnIndex == lastReadColumnIndex) {
            current.position(current.position() - 2);
        } else {
            assert (columnIndex == lastReadColumnIndex+1);
        }
        lastReadColumnIndex = columnIndex;
        short v = current.getShort();
        return v;
    }

    @Override
    public int getInt(int columnIndex) throws SQLException {
        if(columnIndex == lastReadColumnIndex) {
            current.position(current.position() - 4);
        } else {
            assert (columnIndex == lastReadColumnIndex+1);
        }
        lastReadColumnIndex = columnIndex;
        int v = current.getInt();
        return v;
    }

    @Override
    public long getLong(int columnIndex) throws SQLException {
        if(columnIndex == lastReadColumnIndex) {
            current.position(current.position() - 8);
        } else {
            assert (columnIndex == lastReadColumnIndex+1);
        }
        lastReadColumnIndex = columnIndex;
        long v = current.getLong();
        return v;
    }

    @Override
    public double getDouble(int columnIndex) throws SQLException {
        if(columnIndex == lastReadColumnIndex) {
            current.position(current.position() - 8);
        } else {
            assert (columnIndex == lastReadColumnIndex+1);
        }
        lastReadColumnIndex = columnIndex;
        double v = current.getDouble();
        return v;
    }

    @Override
    public String getString(int columnIndex) throws SQLException {
        if(columnIndex == lastReadColumnIndex) {
            current.position(current.position() - (lastStringSize + 2));
        } else {
            assert (columnIndex == lastReadColumnIndex+1);
        }
        lastReadColumnIndex = columnIndex;
        String v = retrieveString(current);

        return v;
    }

    private String retrieveString(ByteBuffer buffer) {
        int bufferLength = buffer.getShort();
        byte[] bytes = new byte[bufferLength];
        buffer.get(bytes);
        lastStringSize = bufferLength;
        return new String(bytes);
    }

    public static void insertString(ByteBuffer buffer, String text) {
        byte[] bytesSize = text == null ? new byte[0] : text.getBytes();
        buffer.putShort((short) bytesSize.length);
        buffer.put(bytesSize);
    }
}

