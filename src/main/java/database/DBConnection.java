package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
   
    private static Connection conn = null;
    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                Properties props = new Properties();
                try (FileInputStream fis = new FileInputStream("db.properties")) {
                    props.load(fis);
                    String url = props.getProperty("db.url");
                    String user = props.getProperty("db.user");
                    String pass = props.getProperty("db.password");
                    Class.forName("org.postgresql.Driver");
                    conn = DriverManager.getConnection(url, user, pass);
                    System.out.println("DATABASE: Connessione stabilita con successo.");
                }
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            System.err.println("ERRORE CONNESSIONE: " + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }
}
