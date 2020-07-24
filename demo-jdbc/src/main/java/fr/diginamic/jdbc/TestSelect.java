package fr.diginamic.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;

import fr.diginamic.jdbc.entites.Fournisseur;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestSelect {


	// Création d'un logger
	private static final Logger LOGGER = Logger.getLogger(TestSelect.class.getName());

	public static void main(String[] args) {

		// recupere le fichier properties
		ResourceBundle db = ResourceBundle.getBundle("database");

		try {

			// enregistre le pilote
			Class.forName(db.getString("db.driver"));

			// créer la connection
			try (Connection connection = DriverManager.getConnection(db.getString("db.url"), db.getString("db.user"),
					db.getString("db.pass"));
				 Statement statement = connection.createStatement();
				 ResultSet resultSet = statement.executeQuery("SELECT * FROM fournisseur");) {


				List<Fournisseur> listeFournisseurs = new ArrayList<>();

				while (resultSet.next()) {
					listeFournisseurs.add(new Fournisseur(resultSet.getInt("id"),resultSet.getString("nom")));
				}

				// affiche les differentes infos de la table fournisseur
				for (Fournisseur fournisseur: listeFournisseurs) {
					LOGGER.log(Level.INFO, fournisseur.toString());
				}

				// pour faire le beau en soirée
				listeFournisseurs.stream()
						.map(Fournisseur::toString)
						.forEach(string-> LOGGER.log(Level.INFO,string));
			}
		} catch (SQLException | ClassNotFoundException e) {
			// Handle errors for JDBC
			LOGGER.log(Level.SEVERE, "Erreur de communication avec la base", e);
		}
	}
}
