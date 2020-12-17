package com.wontanara.dictionnairedelanguedessignes.controller.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.model.Categorie;
import com.wontanara.dictionnairedelanguedessignes.model.CategoriesListe;
import com.wontanara.dictionnairedelanguedessignes.utils.ItemClickSupport;
import com.wontanara.dictionnairedelanguedessignes.view.MyListCategoriesRecyclerViewAdapter;


public class ListCategoriesFragment extends BaseFragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    protected RecyclerView mRecyclerView;
    protected MyListCategoriesRecyclerViewAdapter mAdapter;
    protected CategorieFragment mCategorieFragment;

    public ListCategoriesFragment() {
    }


//    ------ BASE METHODS ------

    @Override
    protected BaseFragment newInstance() {
        ListCategoriesFragment fragment = new ListCategoriesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, mColumnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_categories_list;
    }

    @Override
    protected void configureDesign(View view) {
        this.configureRecyclerView(view);
        this.configureOnClickRecyclerView();
    }

    @Override
    protected void findElements() { }

//    ------ OVERRIDE METHODS ------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        Categorie.lastId = 0;
//    }

//    ------ CONFIGURATION ------
    protected void configureRecyclerView(View view) {
        if (view instanceof RecyclerView) {

            Context context = view.getContext();
            this.mRecyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                // Pas forcement nécessaire pour un affichage seulement en ligne, mais gardé si jamais besoin d'un affichage en grid
                mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            CategoriesListe listeDeCategories = new CategoriesListe();
            mAdapter = new MyListCategoriesRecyclerViewAdapter(listeDeCategories.getListeCategories());
            mRecyclerView.setAdapter(this.mAdapter);
        }
    }

    protected void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(mRecyclerView, R.layout.fragment_categories_in_list)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Categorie mCategorie = mAdapter.getCategorie(position);
                        Toast.makeText(getContext(), "Clic sur la categorie : "+mCategorie.getNom(), Toast.LENGTH_SHORT).show();

//                        Permet de passer dans le bundle du framgent à lancer l'id de la catégorie à afficher
                        mCategorieFragment = new CategorieFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("id-categorie", mCategorie.getId());
                        mCategorieFragment.setArguments(bundle);

                        replaceFragment(mCategorieFragment, R.id.list_categories_frame_layout);

                    }
                });
    }
}