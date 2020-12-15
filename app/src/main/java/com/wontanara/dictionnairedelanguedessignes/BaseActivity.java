package com.wontanara.dictionnairedelanguedessignes;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.google.android.material.navigation.NavigationView;


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
}
