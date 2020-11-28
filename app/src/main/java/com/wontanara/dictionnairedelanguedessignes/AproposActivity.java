package com.wontanara.dictionnairedelanguedessignes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class AproposActivity extends AppCompatActivity {

    // TODO: Remplir la classe
    // TODO: Activer le bouton retour (en haut à gauche)

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apropos);

        configureToolbar();
    }


    private void configureToolbar() {
        // Récupérer visuellement la toolbar dans l'activité
        this.mToolbar = (Toolbar) findViewById(R.id.toolbar);
        this.mToolbar.setTitle(R.string.titre_lien_apropos);
        setSupportActionBar(mToolbar);
    }
}