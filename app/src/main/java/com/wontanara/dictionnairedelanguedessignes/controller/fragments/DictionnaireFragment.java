package com.wontanara.dictionnairedelanguedessignes.controller.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.controller.activities.DictionnaireActivity;
import com.wontanara.dictionnairedelanguedessignes.model.CategoriesListe;
import com.wontanara.dictionnairedelanguedessignes.model.Mot;
import com.wontanara.dictionnairedelanguedessignes.utils.ItemClickSupport;
import com.wontanara.dictionnairedelanguedessignes.view.MyCategorieRecyclerViewAdapter;

import java.util.List;


public class DictionnaireFragment extends BaseFragment {


    private RecyclerView mRecyclerView;
    private MyCategorieRecyclerViewAdapter mAdapter;
    private List<Mot> mMotList;
    private MotFragment mMotFragment;
    private Toolbar mToolbar;


    public DictionnaireFragment() {
        // Required empty public constructor
    }

//    ------ BASE METHODS ------

    @Override
    protected BaseFragment newInstance() {
        return new DictionnaireFragment();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_dictionnaire;
    }

    @Override
    protected void configureDesign(View view) {
        this.configureRecyclerView(view);
        this.configureOnClickRecyclerView();
        this.mToolbar.setTitle(R.string.titre_lien_alphabetique);
    }

    @Override
    protected void findElements(View view) {
//        Temporaire
        CategoriesListe listeDeCategories = new CategoriesListe();
        mMotList = listeDeCategories.getAllMot();
        this.mToolbar = ((DictionnaireActivity) getActivity()).getToolbar();
    }



    //    ------ CONFIGURATION ------
    protected void configureRecyclerView(View view) {
        if (view instanceof RecyclerView) {

            Context context = view.getContext();
            this.mRecyclerView = (RecyclerView) view;
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

            mAdapter = new MyCategorieRecyclerViewAdapter(mMotList);
            mRecyclerView.setAdapter(this.mAdapter);
        }
    }

    protected void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(mRecyclerView, R.layout.fragment_categorie_in_list)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Mot mMot = mAdapter.getMot(position);
                        Toast.makeText(getContext(), "Clic sur le mot : "+ mMot.getNom(), Toast.LENGTH_SHORT).show();

//                        Permet de passer dans le bundle du framgent à lancer l'id du mot à afficher
                        mMotFragment = new MotFragment();
                        Bundle bundle = new Bundle();
//                        bundle.putInt("id-mot", mMot.getId()); // Pour quand les mots auront des ID
                        bundle.putInt("id-mot", position + 1);
                        bundle.putBoolean("liste-entiere", true);
                        mMotFragment.setArguments(bundle);

                        replaceFragment(mMotFragment, R.id.list_mot_frame_layout);

                    }
                });
    }
}