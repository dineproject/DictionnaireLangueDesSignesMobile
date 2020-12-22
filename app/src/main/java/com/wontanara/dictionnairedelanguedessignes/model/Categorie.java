package com.wontanara.dictionnairedelanguedessignes.model;

import java.util.ArrayList;

public class Categorie {

    public static int lastId = 0;
    public int id;
    public String nom;
    public ArrayList<Mot> listeMots;

    public Categorie(String nom, ArrayList<Mot> listeMots) {
        this.id = lastId++;
        this.nom = nom;
        this.listeMots = listeMots;
        lastId++;
    }

    // Temporaire
    public Categorie(String nom) {
        lastId++;
        this.id = lastId;
        this.nom = nom;
        this.listeMots = new ArrayList<Mot>();
        this.listeMots.add(new Mot("Mot" + this.id + ".1", "def"));
        this.listeMots.add(new Mot("Mot" + this.id + ".2", "def"));
        this.listeMots.add(new Mot("Mot" + this.id + ".3", "def"));
        this.listeMots.add(new Mot("Mot" + this.id + ".4", "def"));
        this.listeMots.add(new Mot("Mot" + this.id + ".5", "def"));
        this.listeMots.add(new Mot("Mot" + this.id + ".6", "def"));

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList<Mot> getListeMots() {
        return listeMots;
    }

    public void setListeMots(ArrayList<Mot> listeMots) {
        this.listeMots = listeMots;
    }
}
