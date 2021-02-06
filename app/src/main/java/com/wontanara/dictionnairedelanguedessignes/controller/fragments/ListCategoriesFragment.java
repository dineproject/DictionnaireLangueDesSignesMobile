package com.wontanara.dictionnairedelanguedessignes.controller.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.model.CategoryViewModel;
import com.wontanara.dictionnairedelanguedessignes.utils.ItemClickSupport;
import com.wontanara.dictionnairedelanguedessignes.view.CategoryViewAdapter;

import java.util.Objects;


public class ListCategoriesFragment extends BaseFragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    protected RecyclerView mRecyclerView;
    protected CategoryViewAdapter mAdapter;
    protected CategorieFragment mCategorieFragment;

    private CategoryViewModel mCategoryViewModel;

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
    protected void findElements(View view) { }

//    ------ OVERRIDE METHODS ------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        mCategoryViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity()), ViewModelProvider.AndroidViewModelFactory.getInstance(Objects.requireNonNull(this.getActivity()).getApplication())).get(CategoryViewModel.class);
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

//            CategoriesListe listeDeCategories = new CategoriesListe();
//            mAdapter = new MyListCategoriesRecyclerViewAdapter(listeDeCategories.getListeCategories());
            mAdapter = new CategoryViewAdapter(new CategoryViewAdapter.CategoryDiff());
            mRecyclerView.setAdapter(this.mAdapter);
            mCategoryViewModel.getAllCategories().observe(this, categories -> mAdapter.submitList(categories));
        }
    }

    protected void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(mRecyclerView, R.layout.fragment_categories_in_list)
                .setOnItemClickListener((recyclerView, position, v) -> {
//                        Permet de passer dans le bundle du framgent à lancer l'id de la catégorie à afficher
                    mCategorieFragment = new CategorieFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id-categorie", position);
                    mCategorieFragment.setArguments(bundle);

                    replaceFragment(mCategorieFragment, R.id.list_categories_frame_layout);
                });
    }
}