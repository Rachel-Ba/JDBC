package fr.diginamic.jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import fr.diginamic.jdbc.entites.Fournisseur;
import fr.diginamic.jdbc.exception.ComptaException;

public class FournisseurDaoJdbc implements FournisseurDao 
{
	public static void main(String[] a)
	{
		FournisseurDaoJdbc ofo = new FournisseurDaoJdbc();
		List<Fournisseur> listeFournisseur = ofo.extraire();
		for(Fournisseur fo : listeFournisseur)
		{
			System.out.println(fo);
		}
		
		//////////////INSERT///////////////////////////////////////
		ofo.insert(new Fournisseur(9, "Lesieurs"));
		listeFournisseur = ofo.extraire();
		for(Fournisseur fo : listeFournisseur)
		{
			System.out.println(fo);
		}
		//////////////UPDATE///////////////////////////////////////
		ofo.update("Lesieurs","Leclerc");
		listeFournisseur = ofo.extraire();
		for(Fournisseur fo : listeFournisseur)
		{
			System.out.println(fo);
		}
		//////////////DELETE//////////////////////////////////////
		if(ofo.delete(new Fournisseur(9, "Leclerc")) )System.out.println("Fournisseur Supprimé !");
		listeFournisseur = ofo.extraire();
		for(Fournisseur fo : listeFournisseur)
		{
			System.out.println(fo);
		}
	}

	public List<Fournisseur> extraire() 
	{

		Connection connection = null;
		List<Fournisseur> listeFournisseurs = new ArrayList<>();
		try
		{
			connection = getConnection();
			Statement monCanal = connection.createStatement();
			ResultSet monResultat = monCanal.executeQuery("SELECT * FROM fournisseur;");
			while(monResultat.next())
			{
				listeFournisseurs.add(new Fournisseur(monResultat.getInt("id"), monResultat.getString("nom")));
			}
			monResultat.close();
			monCanal.close();
			connection.close();
		}
		catch(Exception e)
		{
			System.err.println("Erreur d'éxecution : " + e.getMessage());
		}
		finally
		{
			try
			{
				if(connection != null) connection.close();
			}
			catch(SQLException e)
			{
				System.err.println("Probl de connection close : " + e.getMessage());
			}
		}
		return listeFournisseurs;
	}

	/**fait un insert dans la base de compta sur la table fournisseur*/
	public void insert(Fournisseur fournisseur) 
	{
		Connection connection = null;

		try  
		{
			connection = getConnection();
			Statement monCanal = connection.createStatement();
			int nb = monCanal.executeUpdate("insert into fournisseur (id,nom) values ("
			+fournisseur.getId()+",'" + fournisseur.getNom()+"');");
			
			if(nb==1)
			{
				System.out.println("Fournisseur ajouté !");
			}
			monCanal.close();
		} 
		catch(Exception e)
		{
			System.err.println("Erreur d'éxecution : " + e.getMessage());
		}
		finally
		{
			try
			{
				if(connection != null) connection.close();
			}
			catch(SQLException e)
			{
				System.err.println("Probl de connection close : " + e.getMessage());
			}
		}
	}

	/**
	 * fait un update dans la table fournisseur en changeant le nom ancienNom par nouveauNom
	 */
	public int update(String ancienNom, String nouveauNom) 
	{
		Connection connection = null;
		int nb = 0;

		try  
		{
			connection = getConnection();
			Statement monCanal = connection.createStatement();
			nb = monCanal.executeUpdate("UPDATE fournisseur SET nom='" + 
			nouveauNom+"' WHERE nom = '"+ancienNom+"';");
			
			monCanal.close();
			
		} 
		catch(Exception e)
		{
			System.err.println("Erreur d'éxecution : " + e.getMessage());
		}
		finally
		{
			try
			{
				if(connection != null) connection.close();
			}
			catch(SQLException e)
			{
				System.err.println("Probl de connection close : " + e.getMessage());
			}
		}
		return nb;
	}

	/**
	 *supprime le fournisseur specifie dans la table fournisseur
	 */
	public boolean delete(Fournisseur fournisseur) 
	{
		Connection connection = null;
		boolean nb = false;

		try 
		{
			connection = getConnection();
			Statement monCanal = connection.createStatement();
			nb = monCanal.executeUpdate(
					"DELETE FROM fournisseur WHERE id="+ fournisseur.getId()+";")
					==1;
			monCanal.close();
		} 
		catch(Exception e)
		{
			System.err.println("Erreur d'éxecution : " + e.getMessage());
		}
		finally
		{
			try
			{
				if(connection != null) connection.close();
			}
			catch(SQLException e)
			{
				System.err.println("Probl de connection close : " + e.getMessage());
			}
		}
		return nb;
	}

	public Connection getConnection() 
	{
		// recupere le fichier properties
		ResourceBundle db = ResourceBundle.getBundle("database");

		try 
		{
			// enregistre le pilote
			Class.forName(db.getString("db.driver"));

			return DriverManager.getConnection(db.getString("db.url"), db.getString("db.user"),
					db.getString("db.pass"));
		} 
		catch (ClassNotFoundException | SQLException e) 
		{
			throw new ComptaException(e);
		}
	}

}
