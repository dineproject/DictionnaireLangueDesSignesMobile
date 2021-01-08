package com.wontanara.dictionnairedelanguedessignes.controller.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.navigation.NavigationView;
import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.model.Categorie;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    protected DrawerLayout mDrawerLayout;
    protected NavigationView mNavigationView;
    protected Toolbar mToolbar;

    private ImageButton mDicoButton;
    private ImageButton mCategorieButton;
    private ImageButton mSuggestionButton;
    private ImageButton mBaseButton;
    private ImageButton mParametreButton;


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
        this.configureOnClickListener();
    }

    @Override
    protected void findElements() {
    // Enregistre les éléments dont on a besoin au démarrage
        this.mToolbar = (Toolbar) findViewById(R.id.toolbar);
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.main_activity_drawer_layout);
        this.mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        this.mDicoButton = findViewById(R.id.imageDicoButton);
        this.mCategorieButton = findViewById(R.id.imageCategorieButton);
        this.mSuggestionButton = findViewById(R.id.imageSuggestionButton);
        this.mBaseButton = findViewById(R.id.imageBaseButton);
        this.mParametreButton = findViewById(R.id.imageParametreButton);

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
                this.navigationDictionnaire();
                break;
            case R.id.navigation_drawer_categories:
                this.navigationCategories();
                break;
            case R.id.navigation_drawer_suggestions:
                break;
            case R.id.navigation_drawer_bases:
                break;
            case R.id.navigation_drawer_parametres:
                break;
            case R.id.navigation_drawer_apropos:
                this.navigationAPropos();
                break;
            default:
                break;
        }

        this.mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

//    ---- Boutons ----

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageDicoButton:
                this.navigationDictionnaire();
                break;
            case R.id.imageCategorieButton:
                navigationCategories();
                break;
            case R.id.imageSuggestionButton:
                break;
            case R.id.imageBaseButton:
                break;
            case R.id.imageParametreButton:
                break;
            default:
                break;
        }
    }

//    ------ CONFIGURATION ------

    // Configure Drawer Layout
    private void configureDrawerLayout(){
//        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.main_activity_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.description_navigation_drawer_open, R.string.description_navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureOnClickListener(){
        this.mDicoButton.setOnClickListener(this);
        this.mCategorieButton.setOnClickListener(this);
        this.mSuggestionButton.setOnClickListener(this);
        this.mBaseButton.setOnClickListener(this);
        this.mParametreButton.setOnClickListener(this);
    }

}