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
        this.listeMots.add(new Mot("Mot1", "def"));
        this.listeMots.add(new Mot("Mot2", "def"));
        this.listeMots.add(new Mot("Mot3", "def"));
        this.listeMots.add(new Mot("Mot4", "def"));
        this.listeMots.add(new Mot("Mot5", "def"));
        this.listeMots.add(new Mot("Mot6", "def"));

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
