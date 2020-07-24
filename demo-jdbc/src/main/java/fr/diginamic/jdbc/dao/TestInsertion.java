package fr.diginamic.jdbc.dao;


import fr.diginamic.jdbc.entites.Fournisseur;

public class TestInsertion {
	public static void main(String[] args){
		FournisseurDaoJdbc dao = new FournisseurDaoJdbc();
		dao.insert(new Fournisseur(4,"La Maison de la Peinture"));
		dao.insert(new Fournisseur(5,"L’Espace Création"));
	}
}
