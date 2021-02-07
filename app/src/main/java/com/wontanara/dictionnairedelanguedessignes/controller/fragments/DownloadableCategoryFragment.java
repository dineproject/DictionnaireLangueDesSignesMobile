package com.wontanara.dictionnairedelanguedessignes.controller.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.model.ApiViewModel;
import com.wontanara.dictionnairedelanguedessignes.model.CategoryViewModel;
import com.wontanara.dictionnairedelanguedessignes.model.DownloadableCategory;
import com.wontanara.dictionnairedelanguedessignes.utils.ItemClickSupport;
import com.wontanara.dictionnairedelanguedessignes.view.DownloadableCategoryViewAdapter;

import java.util.Objects;

public class DownloadableCategoryFragment extends BaseFragment implements DownloadCategoryDialogFragment.DownloadCategoryDialogListener {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    protected RecyclerView mRecyclerView;
    protected DownloadableCategoryViewAdapter mAdapter;

    private CategoryViewModel mCategoryViewModel;
    private ApiViewModel mApiViewModel;

    public DownloadableCategoryFragment() {
    }

//    ------ BASE METHODS ------
    @Override
    protected BaseFragment newInstance() {
        DownloadableCategoryFragment fragment = new DownloadableCategoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, mColumnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFragmentLayout() { return R.layout.fragment_downloadable_category; }

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
        mApiViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity()), ViewModelProvider.AndroidViewModelFactory.getInstance(Objects.requireNonNull(this.getActivity()).getApplication())).get(ApiViewModel.class);
    }

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

            mAdapter = new DownloadableCategoryViewAdapter(new DownloadableCategoryViewAdapter.DownloadableCategoryDiff());
            mRecyclerView.setAdapter(this.mAdapter);

            mApiViewModel.getAllDownloadableCategories().observe(this, downloadableCategories -> {
                switch (downloadableCategories.status) {
                    case SUCCESS:
                        mAdapter.submitList(downloadableCategories.data);
                        break;
                    case LOADING:
                        break;
                    case ERROR:
                        Toast.makeText(getContext(), "Impossible de récupérer les catégories", Toast.LENGTH_LONG).show();
                }

            });
        }
    }

    protected void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(mRecyclerView, R.layout.fragment_categories_in_list)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    FragmentManager fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                    DownloadCategoryDialogFragment dialog = new DownloadCategoryDialogFragment(mAdapter.getDownloadableCategory(position));
                    dialog.setTargetFragment(this, 0);
                    dialog.show(fm, "downloadDialog");
                });
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, DownloadableCategory downloadableCategory) {
        mApiViewModel.downloadCategory(downloadableCategory).observe(this, categoryWithWords -> {
            switch (categoryWithWords.status) {
                case SUCCESS:
                    mCategoryViewModel.insert(categoryWithWords.data);
                    Toast.makeText(getContext(), "Catégorie téléchargée", Toast.LENGTH_LONG).show();
                    break;
                case LOADING:
                    break;
                case ERROR:
                    Toast.makeText(getContext(), "Impossible de récupérer la catégorie", Toast.LENGTH_LONG).show();
            }

        });
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog, DownloadableCategory downloadableCategory) {

    }
}
