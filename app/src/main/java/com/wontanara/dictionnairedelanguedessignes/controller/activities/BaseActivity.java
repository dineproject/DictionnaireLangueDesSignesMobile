package com.wontanara.dictionnairedelanguedessignes.controller.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;


import com.google.android.material.navigation.NavigationView;
import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.controller.fragments.BaseFragment;


public abstract class BaseActivity extends AppCompatActivity {

//    ------ BASE METHODS ------

    protected abstract int getLayout();
    protected abstract Activity getActivity();
    protected abstract void configureDesign();
    protected abstract void findElements();

//    ------ METHODS OVERRIDE ------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        this.findElements();
        this.configureDesign();
    }


//    ------ DESIGN ------
    protected void configureToolbar(int refTitle) {
        // Récupérer visuellement la toolbar dans l'activité en récupérant l'ID du titre dans strings.xml
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(refTitle);
        setSupportActionBar(mToolbar);
    }

    protected void configureToolbar() {
        // Récupérer visuellement la toolbar dans l'activité
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    protected void configureNavigationView(){
        NavigationView mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) getActivity());
    }


//    ------ FRAGMENTS ------
    protected void showFragment(Fragment fragment, int layout){
        getSupportFragmentManager().beginTransaction()
                .add(layout, fragment)
                .commit();
    }

    protected void replaceFragment(Fragment fragment, int layout){
        getSupportFragmentManager().beginTransaction()
                .replace(layout, fragment)
                .commit();
    }

//    ------ NAVIGATION ------
    protected void navigationAccueil() {
//        i = new Intent(CategoriesActivity.this, MainActivity.class);
//        startActivity(i);
        finish();
    }

    protected void navigationAPropos() {
//        mNavigationView.setNavigationItemSelectedListener(); TODO: voir pour que l'item selectionné pour a propos dans le menu soit quand meme celui de l'accueil
//        en gros quand on va dessus et qu'on revient il dit qu'on y est encore dans le menu
        Intent i = new Intent(getActivity(), AproposActivity.class);
//        ne fonctionne pas
//        item.setChecked(false);
//        this.mNavigationView.getMenu().getItem(0).setChecked(true);
        startActivity(i); // TODO: voir si on peut stop l'activité sans probleme pour le retour ensuite ?
    }

    protected void navigationCategories() {
        Intent i = new Intent(getActivity(), CategoriesActivity.class);
        startActivity(i);
    }

    protected void navigationDictionnaire() {
        Intent i = new Intent(getActivity(), DictionnaireActivity.class);
        startActivity(i);
    }

    protected void navigationParametres() {
        Intent i = new Intent(getActivity(), ParametresActivity.class);
        startActivity(i);
    }
}
