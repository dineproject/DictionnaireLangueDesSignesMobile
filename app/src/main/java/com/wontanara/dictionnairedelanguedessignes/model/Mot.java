package com.wontanara.dictionnairedelanguedessignes.model;

public class Mot {

    public String nom;
    public String definition;

    public Mot(String nom, String definition) {
        this.nom = nom;
        this.definition = definition;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
