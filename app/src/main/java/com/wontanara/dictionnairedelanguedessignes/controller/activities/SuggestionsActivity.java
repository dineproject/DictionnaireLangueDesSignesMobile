package com.wontanara.dictionnairedelanguedessignes.controller.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.wontanara.dictionnairedelanguedessignes.R;

// TODO: faire un bouton (?) dans la barre pour expliquer ce que sont les suggestions ?

public class SuggestionsActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String ARG_MOT_INPUT = "mot-input";

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private TextInputLayout mMotInput;
    private TextInputLayout mDefinitionInput;


//    ------ BASE METHODS ------

    @Override
    protected int getLayout() {
        return R.layout.activity_suggestions;
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected void configureDesign() {
        // Elements graphiques configurés dans onCreate
        this.configureToolbar(R.string.titre_lien_suggestions);
        this.configureDrawerLayout();
        this.configureNavigationView();
        this.configureWordInput();
        this.configureDefinitionInput();
    }

    @Override
    protected void findElements() {
        // Enregistre les éléments dont on a besoin au démarrage
        this.mToolbar = (Toolbar) findViewById(R.id.toolbar);
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.dictionnaire_activity_drawer_layout);
        this.mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        this.mMotInput = (TextInputLayout) findViewById(R.id.word_input_suggestions);
        this.mDefinitionInput = (TextInputLayout) findViewById(R.id.definition_input_suggestion);
    }

//    @Override
//    protected void configureBundle(Bundle savedInstanceState) {
//        if (getIntent().getExtras() != null) {
//            mMotInput.getEditText().setText(getIntent().getExtras().getInt(ARG_MOT_INPUT));
//
//        }
//    }


//    ------ OVERRIDE METHODS ------

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Editable inputmot = this.mMotInput.getEditText().getText();
        bundle.putString(ARG_MOT_INPUT, "a");
//        TODO: pq ca marche pas ...

    }

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


//    ------ CONFIGURATION ------


    // Configure Drawer Layout
    private void configureDrawerLayout(){
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.suggestions_activity_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(SuggestionsActivity.this, mDrawerLayout, mToolbar, R.string.description_navigation_drawer_open, R.string.description_navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureWordInput() {
        mMotInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if (text.length() == 0 ) {
                    mMotInput.setError("Le mot est requis");
                    mMotInput.setErrorEnabled(true);
                } else {
                    mMotInput.setErrorEnabled(false);
//                    TODO: voir pourquoi le compteur va à gauche à ce moment là
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void configureDefinitionInput() {
        mDefinitionInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if (text.length() == 0 ) {
                    mDefinitionInput.setError("La definition est requise");
                    mDefinitionInput.setErrorEnabled(true);
                } else {
                    mDefinitionInput.setErrorEnabled(false);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

}