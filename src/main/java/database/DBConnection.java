package database;

import java.io.IOException;
import java.io.InputStream;
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
                
                try (InputStream is = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
                    if (is == null) {
                        throw new IOException("File db.properties non trovato in src/main/resources");
                    }
                    props.load(is);                   
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
