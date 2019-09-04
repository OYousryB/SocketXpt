package jdbc;
import org.junit.Assert;
import org.junit.Test;
import jdbc.RemoteResultSetWithSequentialColumnAccess.*;

import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;

public class RemoteResultSetTest {

    private byte[] encodeRow(byte[] types, Object... fields) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        {
            int columnCount = types.length;
            int bytesCount = columnCount >> 3;
            if((columnCount & 7) > 0)
                bytesCount ++;
            byte[] nulls = new byte[bytesCount];
            int columnIndex = 0;
            for(Object o: fields) {
                boolean bit = (o==null);
                int byteIndex = columnIndex >> 3;
                nulls[byteIndex] |= bit ? 1 : 0;
                nulls[byteIndex] = (byte)(nulls[byteIndex] << 1);
                columnIndex++;
            }
            nulls[nulls.length-1] = (byte)(nulls[nulls.length-1] << 8 - (columnIndex & 7));
            byteBuffer.put(nulls);
        }
        {
            int columnIndex = 0;
            for(Object o: fields) {
                byte columnType = types[columnIndex];
                switch(columnType){
                    case RemoteResultSetWithSequentialColumnAccess.BOOLEAN_TYPE: byteBuffer.put(((Boolean)o) && o != null ? (byte)1 : (byte)0); break;
                    case RemoteResultSetWithSequentialColumnAccess.TIMESTAMP_TYPE: byteBuffer.putLong(o==null ? 0l : ((Timestamp)o).getTime()); break;
                    case RemoteResultSetWithSequentialColumnAccess.SHORT_TYPE: byteBuffer.putShort(o==null ? 0 : (Short)o); break;
                    case RemoteResultSetWithSequentialColumnAccess.INT_TYPE: byteBuffer.putInt(o==null ? 0 : (Integer)o); break;
                    case RemoteResultSetWithSequentialColumnAccess.LONG_TYPE: byteBuffer.putLong(o==null ? 0 : (Long)o); break;
                    case RemoteResultSetWithSequentialColumnAccess.DOUBLE_TYPE: byteBuffer.putDouble(o==null ? 0 : (Double)o); break;
                    case RemoteResultSetWithSequentialColumnAccess.STRING_TYPE: o = ""; break;   // TODO
                    default:
                        throw new RuntimeException("Unsupported Type " + columnType);
                }
                columnIndex++;
            }
        }
        return Arrays.copyOf(byteBuffer.array(), byteBuffer.position());
    }

    @Test
    public void testTwoIntegerColumnsRetrieveTwoRows() throws SQLException {
        byte[] types = new byte[] {
                RemoteResultSetWithSequentialColumnAccess.INT_TYPE,
                RemoteResultSetWithSequentialColumnAccess.INT_TYPE
        };
        RemoteResultSetWithSequentialColumnAccess resultSet = new RemoteResultSetWithSequentialColumnAccess(types);
        resultSet.addRow(encodeRow(types, 9, 65));
        resultSet.addRow(encodeRow(types, 9*256, 65*256));
        resultSet.setEndOfData(true);
        Assert.assertTrue(resultSet.next());
        Assert.assertEquals(9, resultSet.getInt(1));
        Assert.assertEquals(65, resultSet.getInt(2));
        Assert.assertTrue(resultSet.next());
        Assert.assertEquals(9*256, resultSet.getInt(1));
        Assert.assertEquals(65*256, resultSet.getInt(2));
        Assert.assertFalse(resultSet.next());
    }
}
