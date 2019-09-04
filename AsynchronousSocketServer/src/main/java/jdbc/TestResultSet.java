package jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestResultSet {

    public static void main(String[] args) throws SQLException {
        ResultSet resultSet = new RemoteResultSetWithSequentialColumnAccess();
        while(resultSet.next()) {
            System.out.println(resultSet.getInt(1));
        }
        System.out.println("Done");
    }

}
