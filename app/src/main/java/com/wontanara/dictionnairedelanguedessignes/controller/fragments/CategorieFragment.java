package com.wontanara.dictionnairedelanguedessignes.controller.fragments;

import android.os.Bundle;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.controller.activities.CategoriesActivity;
import com.wontanara.dictionnairedelanguedessignes.model.Categorie;
import com.wontanara.dictionnairedelanguedessignes.model.CategoriesListe;


public class CategorieFragment extends BaseFragment {


    private static final String ARG_ID_CATEGORIE = "id-categorie";
    private int mIdCategorie;
    private Toolbar mToolbar;
    private Categorie mCategorie;

    public CategorieFragment() {
        // Required empty public constructor
    }

//    ------ BASE METHODS ------

    @Override
    protected BaseFragment newInstance() {
        return new CategorieFragment();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_categorie;
    }

    @Override
    protected void configureDesign(View view) {
        this.mToolbar.setTitle(this.mCategorie.getNom());
    }

    @Override
    protected void findElements() {
        this.mToolbar = ((CategoriesActivity) getActivity()).getToolbar();

        // Temporaire
        this.mCategorie = (new CategoriesListe()).getListeCategories().get(mIdCategorie - 1);

    }

//    ------ OVERRIDE METHODS ------
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.mIdCategorie = getArguments().getInt(ARG_ID_CATEGORIE);
        }
        Log.e("TAG TEST", "Id categorie : " + this.mIdCategorie);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(ARG_ID_CATEGORIE, this.mIdCategorie);
    }

    @Override
    public void onStop() {
        super.onStop();
        this.mToolbar.setTitle(R.string.titre_lien_categories);
    }

}