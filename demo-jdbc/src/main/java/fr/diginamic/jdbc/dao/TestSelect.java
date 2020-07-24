package fr.diginamic.jdbc.dao;

import java.util.Iterator;
import java.util.List;

import fr.diginamic.jdbc.entites.Fournisseur;

public class TestSelect {
	public static void main(String[] args) {
		FournisseurDao dao = new FournisseurDaoJdbc();
		List<Fournisseur> fournisseurs = dao.extraire();
		Iterator<Fournisseur> iterator = fournisseurs.iterator();
		while(iterator.hasNext()) {
			Fournisseur fournisseur = iterator.next();
			System.out.println(fournisseur.getId()+" "+fournisseur.getNom());
		}
	}
}
