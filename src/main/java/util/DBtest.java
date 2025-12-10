package util;

import java.sql.Connection;
import java.sql.SQLException;

public class DBtest {
    public static void main(String[] args) {
        DBConnection db = null;
        Connection connection = null;

        try {
            db = DBConnection.getInstance();
            connection = db.getConnection();

            if (connection != null && !connection.isClosed()) {
                System.out.println("Connexion OK !");
            } else {
                System.out.println("La connexion a échoué !");
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion à la base de données :");
            e.printStackTrace();
        }
    }
}

