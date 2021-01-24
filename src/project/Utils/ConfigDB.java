package project.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConfigDB {
    public Connection config() {
        Connection conn = null;
            try {
                String url = "jdbc:sqlserver://DESKTOP-U746ETR\\SQLEXPRESS:1433";
                String username = "PIPpro";
                String password = "12345";
                conn = DriverManager.getConnection(url, username, password);
                if (conn != null) {
                    DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        return conn;

        }
    }

