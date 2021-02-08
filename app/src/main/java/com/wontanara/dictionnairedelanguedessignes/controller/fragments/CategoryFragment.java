package com.wontanara.dictionnairedelanguedessignes.controller.fragments;

import android.content.Context;
import android.os.Bundle;

import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.controller.activities.CategoriesActivity;
import com.wontanara.dictionnairedelanguedessignes.model.CategoryViewModel;
import com.wontanara.dictionnairedelanguedessignes.model.Word;
import com.wontanara.dictionnairedelanguedessignes.utils.ItemClickSupport;
import com.wontanara.dictionnairedelanguedessignes.view.WordViewAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class CategoryFragment extends BaseFragment {


    private static final String ARG_ID_CATEGORY = "id-category";
    private int mIdCategory;

    private Toolbar mToolbar;

    private RecyclerView mRecyclerView;
    private WordViewAdapter mAdapter;
    private WordFragment mWordFragment;

    private CategoryViewModel mCategoryViewModel;


    public CategoryFragment() {
        // Required empty public constructor
    }

//    ------ BASE METHODS ------

    @Override
    protected BaseFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_categorie;
    }

    @Override
    protected void configureDesign(View view) {
        this.configureRecyclerView(view);
        this.configureOnClickRecyclerView();
    }

    @Override
    protected void findElements(View view) {
        this.mToolbar = ((CategoriesActivity) Objects.requireNonNull(getActivity())).getToolbar();
    }

//    ------ OVERRIDE METHODS ------
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.mIdCategory = getArguments().getInt(ARG_ID_CATEGORY);
        }
        mCategoryViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity()), ViewModelProvider.AndroidViewModelFactory.getInstance(Objects.requireNonNull(this.getActivity()).getApplication())).get(CategoryViewModel.class);
    }

    @Override
    public void onSaveInstanceState(@NotNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(ARG_ID_CATEGORY, this.mIdCategory);
    }

    @Override
    public void onStop() {
        super.onStop();
        this.mToolbar.setTitle(R.string.titre_lien_categories);
    }

    //    ------ CONFIGURATION ------
    protected void configureRecyclerView(View view) {
        if (view instanceof RecyclerView) {

            Context context = view.getContext();
            this.mRecyclerView = (RecyclerView) view;
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

            mAdapter = new WordViewAdapter(new WordViewAdapter.WordDiff());
            mRecyclerView.setAdapter(this.mAdapter);

            mCategoryViewModel.getCategoryWithWords(mIdCategory).observe(this, categoryWithWords -> {
                this.mToolbar.setTitle(categoryWithWords.category.getName());
                mAdapter.submitList(categoryWithWords.words);
            });
        }
    }

    protected void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(mRecyclerView, R.layout.fragment_categorie_in_list)
                .setOnItemClickListener((recyclerView, position, v) -> {
                        Word mWord = mAdapter.getWord(position);

//                        Permet de passer dans le bundle du fragment à lancer l'id du mot à afficher
                        mWordFragment = new WordFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("id-mot", mWord.getId());
                        bundle.putBoolean("liste-entiere", false);
                        mWordFragment.setArguments(bundle);

                        replaceFragment(mWordFragment, R.id.list_categories_frame_layout);

                });
    }

}