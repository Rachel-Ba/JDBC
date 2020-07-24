package fr.diginamic.jdbc.dao;

import fr.diginamic.jdbc.entites.Fournisseur;
import fr.diginamic.jdbc.exception.ComptaException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FournisseurDaoJdbcPrepareStatement implements FournisseurDao {

	public List<Fournisseur> extraire() {

		try (Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM fournisseur");
		ResultSet resultSet = statement.executeQuery()) {

			List<Fournisseur> listeFournisseurs = new ArrayList<>();
			while (resultSet.next()) {
				listeFournisseurs.add(new Fournisseur(resultSet.getInt("id"), resultSet.getString("nom")));
			}

			return listeFournisseurs;
		} catch (SQLException e) {
			// transformer SQLException en ComptaException
			throw new ComptaException("Erreur de communication avec la base de données", e);
		}
	}

	/**fait un insert dans la base de compta sur la table fournisseur*/
	public void insert(Fournisseur fournisseur) {

		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement("INSERT INTO fournisseur (id, nom) VALUES (?,?)")) {

			statement.setInt(1, fournisseur.getId());
			statement.setString(2, fournisseur.getNom());
			statement.executeUpdate();

		} catch (SQLException e) {
			throw new ComptaException("Erreur de communication avec la base de données", e);
		}
	}

	/**
	 * fait un update dans la table fournisseur en changeant le nom ancienNom par nouveauNom
	 */
	public int update(String ancienNom, String nouveauNom) {

		try (Connection connection = getConnection();
			 Statement statement = connection.createStatement()) {

			return statement.executeUpdate("UPDATE fournisseur SET nom='"+nouveauNom+"' WHERE nom='"+ancienNom+"'");
		} catch (SQLException e) {
			throw new ComptaException("Erreur de communication avec la base de données", e);
		}
	}

	/**
	 *supprime le fournisseur specifie dans la table fournisseur
	 */
	public boolean delete(Fournisseur fournisseur) {

		try (Connection connection = getConnection();
			 Statement statement = connection.createStatement()) {

			return statement.executeUpdate("DELETE FROM fournisseur where id="+fournisseur.getId()) == 1;
		} catch (SQLException e) {
			throw new ComptaException("Erreur de communication avec la base de données", e);
		}
	}

	public Connection getConnection() {
		// recupere le fichier properties
		ResourceBundle db = ResourceBundle.getBundle("database");

		try {
			// enregistre le pilote
			Class.forName(db.getString("db.driver"));

			return DriverManager.getConnection(db.getString("db.url"), db.getString("db.user"),
					db.getString("db.pass"));
		} catch (ClassNotFoundException | SQLException e) {
			throw new ComptaException(e);
		}
	}

}
