package com.wontanara.dictionnairedelanguedessignes.controller.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.view.MenuItem;

import com.wontanara.dictionnairedelanguedessignes.R;


public class AproposActivity extends BaseActivity {

    private Toolbar mToolbar;


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


//    ----- OVERRIDE METHODS -----

    public boolean onOptionsItemSelected(MenuItem item) {
        this.onBackPressed();
        return true;
    }

}
