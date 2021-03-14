package com.wontanara.dictionnairedelanguedessignes.controller.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.controller.activities.CategoriesActivity;
import com.wontanara.dictionnairedelanguedessignes.model.Category;
import com.wontanara.dictionnairedelanguedessignes.model.CategoryViewModel;
import com.wontanara.dictionnairedelanguedessignes.view.CategoryViewAdapter;

import java.util.List;
import java.util.Objects;


public class CategoriesFragment extends BaseFragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    protected RecyclerView mRecyclerView;
    protected CategoryViewAdapter mAdapter;
    protected CategoryFragment mCategoryFragment;
    protected DownloadableCategoryFragment mDownloadableCategoryFragment;
    protected FloatingActionButton mFloatingActionButton;

    private ActionMode actionMode;
    private ActionCallback actionCallback;

    private CategoryViewModel mCategoryViewModel;

    public CategoriesFragment() {
    }


//    ------ BASE METHODS ------

    @Override
    protected BaseFragment newInstance() {
        CategoriesFragment fragment = new CategoriesFragment();
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
        actionCallback = new ActionCallback();
        ((CategoriesActivity) Objects.requireNonNull(getActivity())).getToolbar().setTitle(R.string.titre_lien_categories);
        mFloatingActionButton.setOnClickListener(view1 -> {
            mDownloadableCategoryFragment = new DownloadableCategoryFragment();
            replaceFragment(mDownloadableCategoryFragment, R.id.list_categories_frame_layout);
        });
        this.configureRecyclerView(view.findViewById(R.id.list));
        this.configureOnClickRecyclerView();
    }

    @Override
    protected void findElements(View view) {
        mFloatingActionButton = view.findViewById(R.id.floatingActionButton);
    }

//    ------ OVERRIDE METHODS ------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        mCategoryViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity()), ViewModelProvider.AndroidViewModelFactory.getInstance(Objects.requireNonNull(this.getActivity()).getApplication())).get(CategoryViewModel.class);
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

            mAdapter = new CategoryViewAdapter(new CategoryViewAdapter.CategoryDiff());
            mRecyclerView.setAdapter(this.mAdapter);
            mCategoryViewModel.getAllCategories().observe(this, categories -> mAdapter.submitList(categories));
        }
    }

    protected void configureOnClickRecyclerView() {
        mAdapter.setItemClick(new CategoryViewAdapter.OnItemClick() {
            @Override
            public void onItemClick(View view, Category category, int position) {
                if (mAdapter.selectedItemCount() == 0) {
                    mCategoryFragment = new CategoryFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id-category", category.getId());
                    mCategoryFragment.setArguments(bundle);

                    replaceFragment(mCategoryFragment, R.id.list_categories_frame_layout);
                } else {
                    toggleActionBar(position);
                }
            }

            @Override
            public void onLongPress(View view, Category category, int position) {
                toggleActionBar(position);
            }
        });
//        ItemClickSupport.addTo(mRecyclerView, R.layout.fragment_categories_in_list)
//                .setOnItemClickListener((recyclerView, position, v) -> {
////                        Permet de passer dans le bundle du framgent à lancer l'id de la catégorie à afficher
//                    Category category = mAdapter.getCategory(position);
//                    mCategoryFragment = new CategoryFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("id-category", category.getId());
//                    mCategoryFragment.setArguments(bundle);
//
//                    replaceFragment(mCategoryFragment, R.id.list_categories_frame_layout);
//                });
    }

    private class ActionCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.categories_select_menu, menu);
            return true;
        }
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.deleteItems) {
                List<Category> selectedItems = mAdapter.getSelectedItems();
                for (int i = selectedItems.size() - 1; i >= 0; i--) {
                    mCategoryViewModel.delete(selectedItems.get(i).getId());
                }
                mAdapter.notifyDataSetChanged();
                mode.finish();
                return true;
            }
            return false;
        }
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mAdapter.clearSelection();
            actionMode = null;
        }
    }

    private void toggleActionBar(int position) {
        if (actionMode == null) {
            actionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(actionCallback);
        }
        toggleSelection(position);
    }

    private void toggleSelection(int position) {
        mAdapter.toggleSelection(position);
        int count = mAdapter.selectedItemCount();
        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }
}