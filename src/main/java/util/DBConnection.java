package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Instance unique
    private static DBConnection instance;
    private Connection connection;
    private final String url = "jdbc:mysql://localhost:3306/dbart25";
    private final String user = "root";
    private final String password = "root";

    // Constructeur privé pour empêcher l'instanciation
    private DBConnection() throws SQLException {
        try {
            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to database.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la DB");
            throw e;
        }
    }

    // Méthode pour récupérer l’instance unique
    public static DBConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    // Getter pour la connection
    public Connection getConnection() {
        return connection;
    }
}
