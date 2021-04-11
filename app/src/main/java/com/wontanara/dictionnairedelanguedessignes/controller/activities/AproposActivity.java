package com.wontanara.dictionnairedelanguedessignes.controller.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;

import com.wontanara.dictionnairedelanguedessignes.R;


public class AproposActivity extends BaseActivity {

    // TODO: Activer le bouton retour (en haut à gauche)

    Toolbar mToolbar;


//    ------ BASE METHODS ------

    @Override
    protected int getLayout() {
        return R.layout.activity_apropos;
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected void configureDesign() {
        this.configureToolbar(R.string.titre_lien_apropos);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void findElements() {
        this.mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }
}