package com.wontanara.dictionnairedelanguedessignes.controller.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.controller.fragments.CategoriesFragment;

public class CategoriesActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout mDrawerLayout;
    protected NavigationView mNavigationView;
    public Toolbar mToolbar;
    protected CategoriesFragment mCategoriesFragment;

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
    }

    @Override
    protected void findElements() {
        // Enregistre les éléments dont on a besoin au démarrage
        this.mToolbar = (Toolbar) findViewById(R.id.toolbar);
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.main_activity_drawer_layout);
        this.mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
    }


//    ------ OVERRIDE METHODS ------


//    ---- Menu ----

    @Override
    public void onBackPressed() {
        // Handle back click to close menu
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
//        } else if (getSupportFragmentManager().findFragmentById(R.id.layout_mot).isInLayout()) {
//            getIntent().removeExtra("id-mot");
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
                this.navigationAccueil();
                break;
            case R.id.navigation_drawer_alphabetique:
                this.navigationDictionnaire();
                break;
            case R.id.navigation_drawer_categories:
                break;
            case R.id.navigation_drawer_suggestions:
                this.navigationSuggestions();
                break;
            case R.id.navigation_drawer_bases:
                this.navigationBases();
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


//    ------ CONFIGURATION ------

    // Configure Drawer Layout
    private void configureDrawerLayout(){
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.categories_activity_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(CategoriesActivity.this, mDrawerLayout, mToolbar, R.string.description_navigation_drawer_open, R.string.description_navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }


//    ---- Fragment ----
    private void configureAndShowListCategoriesFragment(){
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.list_categories_frame_layout);

        if (fragment == null) {
            mCategoriesFragment = new CategoriesFragment();
            this.showFragment(mCategoriesFragment, R.id.list_categories_frame_layout);
        }
    }



    public Toolbar getToolbar() {
        return this.mToolbar;
    }

}