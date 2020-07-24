package fr.diginamic.jdbc;

import TP.TestConnectionJdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestUpdate {

	// Création d'un logger
	private static final Logger LOGGER = Logger.getLogger(TestUpdate.class.getName());

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

				statement.executeUpdate("UPDATE fournisseur SET nom='La Maison des Peintures' WHERE id=4");
				LOGGER.log(Level.INFO, "Fournisseur mis à jour");
			}
		} catch (SQLException | ClassNotFoundException e) {
			// Handle errors for JDBC
			LOGGER.log(Level.SEVERE, "Erreur de communication avec la base", e);
		}
	}

}
