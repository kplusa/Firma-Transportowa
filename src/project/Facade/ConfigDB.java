package project.Facade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConfigDB {
    public Connection config() {
        Connection conn = null;
        try {

            //String url = "jdbc:sqlserver://DESKTOP-TRG3U04\\SQLEXPRESS:1433";
            String url = "jdbc:sqlserver://DESKTOP-U746ETR\\SQLEXPRESS:1433";
            String username = "PIPpro";
            String password = "12345";
            conn = DriverManager.getConnection(url, username, password);
            if (conn != null) {
                System.out.println(conn.getMetaData());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return conn;
    }
}
