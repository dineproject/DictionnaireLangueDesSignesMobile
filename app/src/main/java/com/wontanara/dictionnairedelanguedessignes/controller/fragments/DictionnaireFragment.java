package com.wontanara.dictionnairedelanguedessignes.controller.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.wontanara.dictionnairedelanguedessignes.R;
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
        this.configureRecyclerView(view);
        this.configureOnClickRecyclerView();
        this.mToolbar.setTitle(R.string.titre_lien_alphabetique);
    }

    @Override
    protected void findElements(View view) {
        this.mToolbar = ((DictionnaireActivity) getActivity()).getToolbar();
    }

//    ------ OVERRIDE METHODS ------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWordViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity()), ViewModelProvider.AndroidViewModelFactory.getInstance(Objects.requireNonNull(this.getActivity()).getApplication())).get(WordViewModel.class);
    }


    //    ------ CONFIGURATION ------
    protected void configureRecyclerView(View view) {
        if (view instanceof RecyclerView) {

            Context context = view.getContext();
            this.mRecyclerView = (RecyclerView) view;
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

            mAdapter = new WordViewAdapter(new WordViewAdapter.WordDiff());
            mRecyclerView.setAdapter(this.mAdapter);
            mWordViewModel.getAllWords().observe(this, words -> mAdapter.submitList(words));
        }
    }

    protected void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(mRecyclerView, R.layout.fragment_categorie_in_list)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Word mWord = mAdapter.getWord(position);

//                        Permet de passer dans le bundle du framgent à lancer l'id du mot à afficher
                        mWordFragment = new WordFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("id-mot", mWord.getId());
                        bundle.putBoolean("liste-entiere", true);
                        mWordFragment.setArguments(bundle);

                        replaceFragment(mWordFragment, R.id.list_mot_frame_layout);

                    }
                });
    }
}