package com.wontanara.dictionnairedelanguedessignes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configuration de la toolbar
        this.configureToolbar();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Ajout des boutons à la toolbar
        getMenuInflater().inflate(R.menu.menu_boutons_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Ajout des actions sur les boutons de la toolbar
        switch (item.getItemId()) {
            case R.id.menu_bouton_toolbar_menu:
                Toast.makeText(this, "Ouverture du menu", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_bouton_toolbar_apropos:
                Toast.makeText(this, "Ouverture de A Propos", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void configureToolbar() {
        // Récupérer visuellement la toolbar dans l'activité
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}