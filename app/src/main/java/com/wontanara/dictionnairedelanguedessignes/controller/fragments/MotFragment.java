package com.wontanara.dictionnairedelanguedessignes.controller.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.controller.activities.CategoriesActivity;
import com.wontanara.dictionnairedelanguedessignes.model.Categorie;
import com.wontanara.dictionnairedelanguedessignes.model.CategoriesListe;
import com.wontanara.dictionnairedelanguedessignes.model.Mot;


public class MotFragment extends BaseFragment {

    private static final String ARG_ID_MOT = "id-mot";
    private static final String ARG_ID_CATEGORIE = "id-categorie";
    private int mIdMot;
    private int mIdCategorie;
    private Toolbar mToolbar;
    private Mot mMot;
    private Categorie mCategorie;


    public MotFragment() {
        // Required empty public constructor
    }

//    ------ BASE METHODS ------

    @Override
    protected BaseFragment newInstance() {
        return new MotFragment();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_mot;
    }

    @Override
    protected void configureDesign(View view) {
        this.mToolbar.setTitle(this.mMot.getNom());
    }

    @Override
    protected void findElements() {
        this.mToolbar = ((CategoriesActivity) getActivity()).getToolbar();

        // Temporaire
        this.mMot = (new CategoriesListe()).getListeCategories().get(mIdCategorie).getListeMots().get(mIdMot - 1);

    }

//    ------ OVERRIDE METHODS ------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mIdMot = getArguments().getInt(ARG_ID_MOT);
            this.mIdCategorie = getArguments().getInt(ARG_ID_CATEGORIE);
        }
        Log.e("TAG TEST", "Id Mot : " + this.mIdMot);
        Log.e("TAG TEST", "Id Categorie : " + this.mIdCategorie);

    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(ARG_ID_MOT, this.mIdMot);
    }



}