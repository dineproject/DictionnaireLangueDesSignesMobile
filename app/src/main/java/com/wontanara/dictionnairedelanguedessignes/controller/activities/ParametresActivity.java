package com.wontanara.dictionnairedelanguedessignes.controller.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.controller.fragments.DownloadableCategoryFragment;

import org.json.JSONArray;

public class ParametresActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    protected DrawerLayout mDrawerLayout;
    protected NavigationView mNavigationView;
    public Toolbar mToolbar;
    protected DownloadableCategoryFragment mDownloadableCategoryFragment;

    private Button mApiButton;

//    ------ BASE METHODS ------

    @Override
    protected int getLayout() {
        return R.layout.activity_parametres;
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected void configureDesign() {
        // Elements graphiques configurés dans onCreate
        this.configureToolbar(R.string.titre_lien_parametres);
        this.configureDrawerLayout();
        this.configureNavigationView();
        this.mNavigationView.getMenu().getItem(4).setChecked(true);
        this.configureAndShowListCategoriesFragment();
        this.configureOnClickListener();

    }

    @Override
    protected void findElements() {
        // Enregistre les éléments dont on a besoin au démarrage
        this.mToolbar = (Toolbar) findViewById(R.id.toolbar);
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.parametres_activity_drawer_layout);
        this.mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        this.mApiButton = (Button) findViewById(R.id.button_api_call);
    }

//    ------ OVERRIDE METHODS ------

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
                this.navigationAccueil();
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
            case R.id.button_api_call:
                this.testFunction();
                break;
            default:
                break;
        }
    }

//    ------ CONFIGURATION ------

    // Configure Drawer Layout
    private void configureDrawerLayout(){
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.parametres_activity_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(ParametresActivity.this, mDrawerLayout, mToolbar, R.string.description_navigation_drawer_open, R.string.description_navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureOnClickListener(){
        this.mApiButton.setOnClickListener(this);
    }

    private void testFunction(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://ea-perso.ovh/api/category";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // TODO: Create list of downloadable categories
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {  }
        });

        queue.add(jsonArrayRequest);
    }

//    ---- Fragment ----
    private void configureAndShowListCategoriesFragment(){
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.parameters_frame_layout);

        if (fragment == null) {
            mDownloadableCategoryFragment = new DownloadableCategoryFragment();
            this.showFragment(mDownloadableCategoryFragment, R.id.parameters_frame_layout);
        }
    }
}