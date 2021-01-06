package com.wontanara.dictionnairedelanguedessignes.controller.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.controller.activities.CategoriesActivity;
import com.wontanara.dictionnairedelanguedessignes.controller.activities.VideoPlayerActivity;
import com.wontanara.dictionnairedelanguedessignes.model.Categorie;
import com.wontanara.dictionnairedelanguedessignes.model.CategoriesListe;
import com.wontanara.dictionnairedelanguedessignes.model.Mot;


public class MotFragment extends BaseFragment implements View.OnClickListener{

    private static final String ARG_ID_MOT = "id-mot";
    private static final String ARG_ID_CATEGORIE = "id-categorie";
    public final static String EXTRA_MOT = "nom-mot";
    private int mIdMot;
    private int mIdCategorie;
    private Toolbar mToolbar;
    private Mot mMot;
    private Categorie mCategorie;

    private TextView mTextViewTitre;
    private TextView mTextViewDefinition;


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
        view.findViewById(R.id.imageButton_play_video).setOnClickListener(this);
        this.configureTextView();
    }

    @Override
    protected void findElements(View view) {
        this.mToolbar = ((CategoriesActivity) getActivity()).getToolbar();
        this.mTextViewTitre = (TextView) view.findViewById(R.id.titre_mot);
        this.mTextViewDefinition = (TextView) view.findViewById(R.id.definition_mot);

        // Temporaire
        this.mMot = (new CategoriesListe()).getListeCategories().get(mIdCategorie - 1).getListeMots().get(mIdMot - 1);

    }

//    ------ OVERRIDE METHODS ------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mIdMot = getArguments().getInt(ARG_ID_MOT);
            this.mIdCategorie = getArguments().getInt(ARG_ID_CATEGORIE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(ARG_ID_MOT, this.mIdMot);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(getActivity(), VideoPlayerActivity.class);
        i.putExtra(EXTRA_MOT, mMot.getNom());
        startActivityForResult(i, 0);

    }


//    // création du callback pour pointer vers l'activité
//    private void createCallbackToParentActivity(){
//        try {
//            //Parent activity will automatically subscribe to callback
//            mCallback = (MotFragment.OnButtonClickedListener) getActivity();
//        } catch (ClassCastException e) {
//            throw new ClassCastException(e.toString()+ " must implement OnButtonClickedListener");
//        }
//    }



//    ------ CONFIGURATION ------

    public void configureTextView() {
        mTextViewTitre.setText(mMot.getNom());
        mTextViewDefinition.setText(mMot.getDefinition());
    }


}