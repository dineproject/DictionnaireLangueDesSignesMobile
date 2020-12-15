package com.wontanara.dictionnairedelanguedessignes;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout mDrawerLayout;
    protected NavigationView mNavigationView;
    protected Toolbar mToolbar;


//    ------ BASE METHODS ------

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected void configureDesign() {
    // Elements graphiques configurés dans onCreate
        this.configureToolbar();
        this.configureDrawerLayout();
        this.configureNavigationView();
        this.mNavigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    protected void findElements() {
    // Enregistre les éléments dont on a besoin au démarrage
        this.mToolbar = (Toolbar) findViewById(R.id.toolbar);
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.main_activity_drawer_layout);
        this.mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
    }


//    ------ OVERRIDE METHODS ------
//    ---- Toolbar ----

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Ajout des boutons à la toolbar
        getMenuInflater().inflate(R.menu.menu_boutons_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Ajout des actions sur les boutons de la toolbar
        if (item.getItemId() == R.id.menu_bouton_toolbar_apropos) {
            Intent i = new Intent(MainActivity.this, AproposActivity.class);
            startActivity(i);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

//    ---- Menu ----

    @Override
    public void onBackPressed() {
        // Handle back click to close menu
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Actions au clic dans les items du menu
        int id = item.getItemId();

        switch (id){ //TODO: R.id. etc a voir par quoi on change
            case R.id.navigation_drawer_accueil:
                break;
            case R.id.navigation_drawer_alphabetique:
                break;
            case R.id.navigation_drawer_categories:
                break;
            case R.id.navigation_drawer_suggestions:
                break;
            case R.id.navigation_drawer_bases:
                break;
            case R.id.navigation_drawer_parametres:
                break;
            case R.id.navigation_drawer_apropos:
//                mNavigationView.setNavigationItemSelectedListener(); TODO: voir pour que l'item selectionné pour a propos dans le menu soit quand meme celui de l'accueil
//                en gros quand on va dessus et qu'on revient il dit qu'on y est encore dans le menu
                Intent i = new Intent(MainActivity.this, AproposActivity.class);
//                ne fonctionne pas
//                item.setChecked(false);
//                this.mNavigationView.getMenu().getItem(0).setChecked(true);
                startActivity(i); // TODO: voir si on peut stop l'activité sans probleme pour le retour ensuite ?
                break;
            default:
                break;
        }

        this.mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }


    // Configure Drawer Layout
    private void configureDrawerLayout(){
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.main_activity_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.description_navigation_drawer_open, R.string.description_navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
}