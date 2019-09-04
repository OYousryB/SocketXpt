package jdbc;
import org.junit.Assert;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;

public class RemoteResultSetTest {

    public static final byte B = RemoteResultSetWithSequentialColumnAccess.BOOLEAN_TYPE;
    public static final byte T = RemoteResultSetWithSequentialColumnAccess.TIMESTAMP_TYPE;
    public static final byte S = RemoteResultSetWithSequentialColumnAccess.SHORT_TYPE;
    public static final byte I = RemoteResultSetWithSequentialColumnAccess.INT_TYPE;
    public static final byte L = RemoteResultSetWithSequentialColumnAccess.LONG_TYPE;
    public static final byte D = RemoteResultSetWithSequentialColumnAccess.DOUBLE_TYPE;
    public static final byte R = RemoteResultSetWithSequentialColumnAccess.STRING_TYPE;

    @DataProvider
    public Object[][] getData() {
        return new Object[][]{
                { new Object[]{5, 10, 20}, new byte[] { I, I, I }},
                { new Object[]{5L, 10L, 20L}, new byte[] { L, L, L }},
                { new Object[]{5L, 10L, 20L, 5, 10, 20}, new byte[] { L, L, L, I, I, I }},
                { new Object[]{9, null, 512}, new byte[] { I, I, I }},
                { new Object[]{null, null, null}, new byte[] { I, I, I }}
        };
    }

    private byte[] encodeRow(byte[] types, Object[] fields) {
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
            nulls[nulls.length-1] = (byte)(nulls[nulls.length-1] << (8 - columnIndex) & 7);
            byteBuffer.put(nulls);
        }
        {
            int columnIndex = 0;
            for(Object o: fields) {
                byte columnType = types[columnIndex];
                switch(columnType){
                    case RemoteResultSetWithSequentialColumnAccess.BOOLEAN_TYPE: byteBuffer.put(((Boolean)o) && o != null ? (byte)1 : (byte)0); break;
                    case RemoteResultSetWithSequentialColumnAccess.TIMESTAMP_TYPE: byteBuffer.putLong(o==null ? 0L : ((Timestamp)o).getTime()); break;
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

    private <T> Object nullAsZero(T i) {
        return i == null ? 0 : i;
    }

    interface ResultSetGetter {
        Object func(ResultSet r, int columnIndex);
    }

    ResultSetGetter findGetter(byte type){
        switch (type) {
            case I:
                return  (r, i) -> {
                    try {
                        return (Integer)r.getInt(i);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                };
            case L:
                return  (r, i) -> {
                    try {
                        return (Long)r.getLong(i);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                };
        }
        return null;
    }

    @Test(dataProvider= "getData")
    public void testRetrievePrimitiveFromMultipleRows(Object[] data, byte[] types) throws SQLException {
        RemoteResultSetWithSequentialColumnAccess resultSet = new RemoteResultSetWithSequentialColumnAccess(types);
        resultSet.addRow(encodeRow(types, data));
        resultSet.addRow(encodeRow(types, data));
        resultSet.setEndOfData(true);
        Assert.assertTrue(resultSet.next());
        for(int c=0; c<types.length; c++) {
            Assert.assertEquals(nullAsZero(data[c]), nullAsZero(findGetter(types[c]).func(resultSet, c+1)));
        }
        Assert.assertTrue(resultSet.next());
        for(int c=0; c<types.length; c++) {
            Assert.assertEquals(nullAsZero(data[c]), nullAsZero(findGetter(types[c]).func(resultSet, c+1)));
        }
        Assert.assertFalse(resultSet.next());
    }

    @Test(dataProvider= "getData")
    public void testRetrieveObjectThenPrimitiveFromSingleRow(Object[] data, byte[] types) throws SQLException {
        RemoteResultSetWithSequentialColumnAccess resultSet = new RemoteResultSetWithSequentialColumnAccess(types);
        resultSet.addRow(encodeRow(types, data));
        resultSet.setEndOfData(true);
        Assert.assertTrue(resultSet.next());
        for(int c=0; c<types.length; c++) {
            Assert.assertEquals(nullAsZero(data[c]), resultSet.getObject(c+1));
            Assert.assertEquals(nullAsZero(data[c]), nullAsZero(findGetter(types[c]).func(resultSet, c+1)));
        }
        Assert.assertFalse(resultSet.next());
    }
}
