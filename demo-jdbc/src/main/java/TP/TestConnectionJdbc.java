package TP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestConnectionJdbc {

    // Création d'un logger
    private static final Logger LOGGER = Logger.getLogger(TestConnectionJdbc.class.getName());

    public static void main(String[] args) {

        // recupere le fichier properties
        ResourceBundle db = ResourceBundle.getBundle("database");

        try {

            // enregistre le pilote
            Class.forName(db.getString("db.driver"));

            // creer la connection
            try (Connection connection = DriverManager.getConnection(db.getString("db.url"), db.getString("db.user"),
                    db.getString("db.pass"))) {

                // affiche la connexion
                boolean valid = connection.isValid(500);
                if (valid) {
                    // SEVERE = Erreur
                    // INFO = info
                    // WARNING = Avertissement
                    // FINEST = Debug
                    LOGGER.log(Level.INFO, "La connection est ok");
                } else {
                    LOGGER.log(Level.SEVERE, "Il y a une erreur de connection");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Handle errors for JDBC
            LOGGER.log(Level.SEVERE, "Erreur de communication avec la base", e);
        }
    }

}
