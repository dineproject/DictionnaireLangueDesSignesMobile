package com.wontanara.dictionnairedelanguedessignes.model;

public class Mot {

    public String nom;
    public String definition;

    public Mot(String nom, String definition) {
        this.nom = nom;
        this.definition = definition;
    }

    public Mot(String nom) {
        this.nom = nom;
        this.definition = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor";
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDefinition() {
        return this.definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
