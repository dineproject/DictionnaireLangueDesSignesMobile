package com.wontanara.dictionnairedelanguedessignes.controller.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.controller.activities.BaseActivity;
import com.wontanara.dictionnairedelanguedessignes.controller.activities.DictionnaireActivity;
import com.wontanara.dictionnairedelanguedessignes.model.Word;
import com.wontanara.dictionnairedelanguedessignes.model.WordViewModel;
import com.wontanara.dictionnairedelanguedessignes.utils.ItemClickSupport;
import com.wontanara.dictionnairedelanguedessignes.view.WordViewAdapter;

import java.util.Objects;


public class DictionnaireFragment extends BaseFragment {


    private RecyclerView mRecyclerView;
    private WordViewAdapter mAdapter;
    private WordFragment mWordFragment;
    private Toolbar mToolbar;
    protected TextView mEmptyView;

    private WordViewModel mWordViewModel;

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
        return R.layout.fragment_categorie;
    }

    @Override
    protected void configureDesign(View view) {
        this.configureRecyclerView(view.findViewById(R.id.word_list));
        this.configureOnClickRecyclerView();
        this.mToolbar.setTitle(R.string.titre_lien_alphabetique);
    }

    @Override
    protected void findElements(View view) {
        this.mToolbar = ((DictionnaireActivity) getActivity()).getToolbar();
        mEmptyView = view.findViewById(R.id.empty_view);
    }

//    ------ OVERRIDE METHODS ------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mWordViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity()), ViewModelProvider.AndroidViewModelFactory.getInstance(Objects.requireNonNull(this.getActivity()).getApplication())).get(WordViewModel.class);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = new SearchView(((BaseActivity) getActivity()).getSupportActionBar().getThemedContext());
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        item.setActionView(searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnClickListener(v -> {

        }
        );
    }


    //    ------ CONFIGURATION ------
    protected void configureRecyclerView(View view) {
        if (view instanceof RecyclerView) {

            Context context = view.getContext();
            this.mRecyclerView = (RecyclerView) view;
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

            mAdapter = new WordViewAdapter(new WordViewAdapter.WordDiff());
            mRecyclerView.setAdapter(this.mAdapter);
            mWordViewModel.getAllWords().observe(this, words -> {
                mAdapter.submitList(words);
                if (words.isEmpty()) {
                    mEmptyView.setVisibility(View.VISIBLE);
                    view.setVisibility(View.GONE);
                }
            });
        }
    }

    protected void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(mRecyclerView, R.layout.fragment_categorie_in_list)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    Word mWord = mAdapter.getWord(position);

//                        Permet de passer dans le bundle du framgent à lancer l'id du mot à afficher
                    mWordFragment = new WordFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id-mot", mWord.getId());
                    bundle.putBoolean("liste-entiere", true);
                    mWordFragment.setArguments(bundle);

                    replaceFragment(mWordFragment, R.id.list_mot_frame_layout);

                });
    }
}