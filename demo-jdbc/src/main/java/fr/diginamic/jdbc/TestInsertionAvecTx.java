package fr.diginamic.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestInsertionAvecTx {

    // Création d'un logger
    private static final Logger LOGGER = Logger.getLogger(TestInsertionAvecTx.class.getName());

    public static void main(String[] args) {

        // recupere le fichier properties
        ResourceBundle db = ResourceBundle.getBundle("database");

        try {

            // enregistre le pilote
            Class.forName(db.getString("db.driver"));

            // créer la connection
            try (Connection connection = DriverManager.getConnection(db.getString("db.url"), db.getString("db.user"),
                    db.getString("db.pass"));
                 Statement statement = connection.createStatement()) {

                // activer la possibilité de faire des transactions
                connection.setAutoCommit(false);

                try {
                    statement.executeUpdate("INSERT INTO fournisseur (id, nom) VALUES (41, 'La Maison de la Peinture') ");
                    statement.executeUpdate("INSERT INTO fournisseur (id, nom) VALUES (51, 'La Maison de la Peinture') ");
                    statement.executeUpdate("INSERT INTO fournisseur (id, nom) VALUES (60, 'La Maison de la Peinture') ");
                }catch (SQLException e) {
                    connection.rollback(); // on annule tout
                }
                connection.commit();

                // exemple du virement A vers B
                // T1
                // débit sur un compte A -> update OK
                // crédit sur le compte B -> update KO
                // T1.commit => valide => tout est OK
                // T1.rollback => on annule tout




               // statement.executeUpdate("INSERT INTO fournisseur (id, nom) VALUES (4, 'La Maison de la Peinture') ");
                LOGGER.log(Level.INFO, "Nouveau fournisseur inséré");
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Handle errors for JDBC
            LOGGER.log(Level.SEVERE, "Erreur de communication avec la base", e);
        }
    }

}
