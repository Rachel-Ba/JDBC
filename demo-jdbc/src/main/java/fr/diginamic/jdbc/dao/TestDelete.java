package fr.diginamic.jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import fr.diginamic.jdbc.entites.Fournisseur;

public class TestDelete {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		FournisseurDaoJdbc dao = new FournisseurDaoJdbc();
		dao.delete(new Fournisseur(4,"La Maison des Peintures"));
	}
}
