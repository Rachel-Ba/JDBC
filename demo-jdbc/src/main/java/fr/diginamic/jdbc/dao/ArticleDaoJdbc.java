package fr.diginamic.jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import fr.diginamic.jdbc.entites.Article;
import fr.diginamic.jdbc.exception.ComptaException;

public class ArticleDaoJdbc implements ArticleDao 
{
	public static void main(String[] a)
	{
		ArticleDaoJdbc ofo = new ArticleDaoJdbc();
		List<Article> listeArticle = ofo.extraire();
		for(Article ar : listeArticle)
		{
			System.out.println(ar);
		}
		
		//////////////INSERT///////////////////////////////////////
		ofo.insert(new Article(14 , "Z01", "TEST T1", 666.66, 1));
		listeArticle = ofo.extraire();
		for(Article ar : listeArticle)
		{
			System.out.println(ar);
		}
	
		//////////////UPDATE///////////////////////////////////////
		ofo.update("Z01","Z66");
		listeArticle = ofo.extraire();
		for(Article ar : listeArticle)
		{
			System.out.println(ar);
		}
	
		//////////////DELETE//////////////////////////////////////
		if(ofo.delete(new Article(14 , "Z66", "TEST T1", 666.66, 1)) )System.out.println("Article Supprimé !");
		listeArticle = ofo.extraire();
		for(Article ar : listeArticle)
		{
			System.out.println(ar);
		}
	}

	public List<Article> extraire() 
	{

		Connection connection = null;
		List<Article> listeArticles = new ArrayList<>();
		try
		{
			connection = getConnection();
			Statement monCanal = connection.createStatement();
			ResultSet monResultat = monCanal.executeQuery("SELECT * FROM article;");
			while(monResultat.next())
			{
				listeArticles.add(new Article(monResultat.getInt("id"), monResultat.getString("ref"),
						monResultat.getString("designation"), monResultat.getDouble("prix"),
						monResultat.getInt("id_fou")));
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
		return listeArticles;
	}

	/**fait un insert dans la base de compta sur la table Article*/
	public void insert(Article Article) 
	{
		Connection connection = null;

		try  
		{
			connection = getConnection();
			Statement monCanal = connection.createStatement();
			int nb = monCanal.executeUpdate("INSERT INTO article (id,ref,designation,prix,id_fou) values ("
			+Article.getId()+",'" + 
					Article.getRef()+"','" +Article.getDesignation()+"'," +
					Article.getPrix()+"," +Article.getId_fou()+");");
			
			if(nb==1)
			{
				System.out.println("Article ajouté !");
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
	 * fait un update dans la table Article en changeant le nom ancienNom par nouveauNom
	 */
	public int update(String ancienNom, String nouveauNom) 
	{
		Connection connection = null;
		int nb = 0;

		try  
		{
			connection = getConnection();
			Statement monCanal = connection.createStatement();
			nb = monCanal.executeUpdate("UPDATE Article SET ref ='" + 
			nouveauNom+"' WHERE ref = '"+ancienNom+"';");
			
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
	 *supprime le Article specifie dans la table Article
	 */
	public boolean delete(Article Article) 
	{
		Connection connection = null;
		boolean nb = false;

		try 
		{
			connection = getConnection();
			Statement monCanal = connection.createStatement();
			nb = monCanal.executeUpdate(
					"DELETE FROM Article WHERE id="+ Article.getId()+";")
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
