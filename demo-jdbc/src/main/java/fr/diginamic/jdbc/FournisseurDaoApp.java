package fr.diginamic.jdbc;

import fr.diginamic.jdbc.dao.FournisseurDao;
import fr.diginamic.jdbc.dao.FournisseurDaoJdbc;
import fr.diginamic.jdbc.entites.Fournisseur;
import fr.diginamic.jdbc.exception.ComptaException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FournisseurDaoApp {

    private static final Logger LOGGER = Logger.getLogger(FournisseurDaoApp.class.getName());


    public static void main(String[] args) {

        try {
            // Interface var = new Implementation()
            FournisseurDao fournisseurDao = new FournisseurDaoJdbc();

            List<Fournisseur> fournisseurList = fournisseurDao.extraire();

            for (Fournisseur fourn : fournisseurList) {
                LOGGER.log(Level.INFO, fourn.toString());
            }
        } catch (ComptaException e) {
            LOGGER.log(Level.SEVERE, "Une erreur inattendue s'est produite", e);
        }
    }
}
