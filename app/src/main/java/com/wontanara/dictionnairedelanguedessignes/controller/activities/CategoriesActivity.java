package com.wontanara.dictionnairedelanguedessignes.controller.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.controller.fragments.ListCategoriesFragment;
import com.wontanara.dictionnairedelanguedessignes.model.Categorie;

public class CategoriesActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout mDrawerLayout;
    protected NavigationView mNavigationView;
    protected Toolbar mToolbar;
    protected ListCategoriesFragment mListCategoriesFragment;

//    ------ BASE METHODS ------

    @Override
    protected int getLayout() {
        return R.layout.activity_categories;
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected void configureDesign() {
        // Elements graphiques configurés dans onCreate
        this.configureToolbar(R.string.titre_lien_categories);
        this.configureDrawerLayout();
        this.configureNavigationView();
        this.configureAndShowListCategoriesFragment();
        this.mNavigationView.getMenu().getItem(1).getSubMenu().getItem(1).setChecked(true);
    }

    @Override
    protected void findElements() {
        // Enregistre les éléments dont on a besoin au démarrage
        this.mToolbar = (Toolbar) findViewById(R.id.toolbar);
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.main_activity_drawer_layout);
        this.mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
    }


//    ------ OVERRIDE METHODS ------
    @Override
    public void onDestroy() {
        super.onDestroy();
        Categorie.lastId = 0;
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
        Intent i;

        switch (id){ //TODO: R.id. etc a voir par quoi on change
            case R.id.navigation_drawer_accueil:
//                i = new Intent(CategoriesActivity.this, MainActivity.class);
//                startActivity(i);
                finish();
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
//              TODO: Comme dans MainActivity
                i = new Intent(CategoriesActivity.this, AproposActivity.class);
                startActivity(i);
                break;
            default:
                break;
        }

        this.mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }


    // Configure Drawer Layout
    private void configureDrawerLayout(){
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.categories_activity_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(CategoriesActivity.this, mDrawerLayout, mToolbar, R.string.description_navigation_drawer_open, R.string.description_navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }


//    ---- Fragment ----
    private void configureAndShowListCategoriesFragment(){
        mListCategoriesFragment = (ListCategoriesFragment) getSupportFragmentManager().findFragmentById(R.id.list_categories_frame_layout);

        if (mListCategoriesFragment == null) {
            mListCategoriesFragment = new ListCategoriesFragment();
            this.showFragment(mListCategoriesFragment, R.id.list_categories_frame_layout);
        }
    }

}