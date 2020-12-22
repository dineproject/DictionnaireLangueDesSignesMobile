package com.wontanara.dictionnairedelanguedessignes.model;

import java.util.ArrayList;
import java.util.List;

public class CategoriesListe {
    public ArrayList<Categorie> listeCategories;

    public CategoriesListe(ArrayList<Categorie> listeCategories) {
        this.listeCategories = listeCategories;
    }

    // Temporaire
    public CategoriesListe() {
        Categorie.lastId=0;
        this.listeCategories = new ArrayList<Categorie>();
        this.listeCategories.add(new Categorie("Categorie A"));
        this.listeCategories.add(new Categorie("Categorie B"));
        this.listeCategories.add(new Categorie("Categorie C"));
        this.listeCategories.add(new Categorie("Categorie D"));
        this.listeCategories.add(new Categorie("Categorie E"));
        this.listeCategories.add(new Categorie("Categorie F"));
        this.listeCategories.add(new Categorie("Categorie G"));
        this.listeCategories.add(new Categorie("Categorie H"));
        this.listeCategories.add(new Categorie("Categorie I"));
        this.listeCategories.add(new Categorie("Categorie J"));
        this.listeCategories.add(new Categorie("Categorie K"));
        this.listeCategories.add(new Categorie("Categorie L"));
        this.listeCategories.add(new Categorie("Categorie M"));
        this.listeCategories.add(new Categorie("Categorie N"));

    }

    public ArrayList<Categorie> getListeCategories() {
        return listeCategories;
    }

    public void setListeCategories(ArrayList<Categorie> listeCategories) {
        this.listeCategories = listeCategories;
    }

    // Temporaire
//    TODO: A tester
    public List<Mot> getAllMot () {
        ArrayList<Mot> listAll = new ArrayList<Mot>();

        for (Categorie categorie : this.listeCategories) {
            listAll.addAll(categorie.listeMots);
        }

        return listAll;
    }
}
