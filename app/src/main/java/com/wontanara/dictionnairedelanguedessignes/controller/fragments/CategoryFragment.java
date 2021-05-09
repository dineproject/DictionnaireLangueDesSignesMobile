package com.wontanara.dictionnairedelanguedessignes.controller.fragments;

import android.content.Context;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.controller.activities.BaseActivity;
import com.wontanara.dictionnairedelanguedessignes.controller.activities.CategoriesActivity;
import com.wontanara.dictionnairedelanguedessignes.model.CategoryViewModel;
import com.wontanara.dictionnairedelanguedessignes.model.Word;
import com.wontanara.dictionnairedelanguedessignes.utils.ItemClickSupport;
import com.wontanara.dictionnairedelanguedessignes.view.WordViewAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class CategoryFragment extends BaseFragment {

    private SearchView searchView;

    private static final String ARG_ID_CATEGORY = "id-category";
    private int mIdCategory;

    private Toolbar mToolbar;

    private RecyclerView mRecyclerView;
    private WordViewAdapter mAdapter;
    private WordFragment mWordFragment;
    protected TextView mEmptyView;

    private CategoryViewModel mCategoryViewModel;

    private MutableLiveData<String> searchStringLiveData = new MutableLiveData<>("");


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
        this.configureRecyclerView(view.findViewById(R.id.word_list));
        this.configureOnClickRecyclerView();
    }

    @Override
    protected void findElements(View view) {
        this.mToolbar = ((CategoriesActivity) Objects.requireNonNull(getActivity())).getToolbar();
        mEmptyView = view.findViewById(R.id.empty_view);
    }

//    ------ OVERRIDE METHODS ------
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView = new SearchView(((BaseActivity) getActivity()).getSupportActionBar().getThemedContext());
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        item.setActionView(searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchStringLiveData.setValue(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchStringLiveData.setValue(newText);
                return false;
            }
        });
        searchView.setOnClickListener(v -> {

        }
        );
        if (!TextUtils.isEmpty(searchStringLiveData.getValue())) {
            searchView.setQuery(searchStringLiveData.getValue(), false);
            searchView.setIconified(false);
            searchView.clearFocus();
        }
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
                searchStringLiveData.observe(this, string -> {
                    ArrayList<Word> list = new ArrayList<>();
                    if (TextUtils.isEmpty(string)) {
                        list.addAll(categoryWithWords.words);
                    } else {
                        for (Word word: categoryWithWords.words) {
                            if (word.getName().toLowerCase().contains(string.toLowerCase())) {
                                list.add(word);
                            }
                        }
                    }
                    mAdapter.submitList(list);
                    if (list.isEmpty()) {
                        mEmptyView.setVisibility(View.VISIBLE);
                        view.setVisibility(View.GONE);
                    } else {
                        mEmptyView.setVisibility(View.GONE);
                        view.setVisibility(View.VISIBLE);
                    }
                });
            });
        }
    }

    protected void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(mRecyclerView, R.layout.fragment_categorie_in_list)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    searchView.clearFocus();
                    Word mWord = mAdapter.getWord(position);

//                    Permet de passer dans le bundle du fragment à lancer l'id du mot à afficher
                    mWordFragment = new WordFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id-mot", mWord.getId());
                    bundle.putBoolean("liste-entiere", false);
                    mWordFragment.setArguments(bundle);

                    replaceFragment(mWordFragment, R.id.list_categories_frame_layout);

                });
    }

}