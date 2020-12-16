package com.wontanara.dictionnairedelanguedessignes.controller.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.model.CategoriesListe;


public class ListCategoriesFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    public ListCategoriesFragment() {
    }

    public static ListCategoriesFragment newInstance(int columnCount) {
        ListCategoriesFragment fragment = new ListCategoriesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }


//    ------ BASE METHODS ------

//    @Override
//    protected BaseFragment newInstance() {
//        ListCategoriesFragment fragment = new ListCategoriesFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_COLUMN_COUNT, mColumnCount);
//        fragment.setArguments(args);
//        return fragment;
//    }

//    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_categories_list;
    }

//    @Override
//    protected void configureDesign(View view) {
//        this.configureAdaptater(view);
//    }


//    ------ OVERRIDE METHODS ------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            CategoriesListe listeDeCategories = new CategoriesListe();
            recyclerView.setAdapter(new MyListCategoriesRecyclerViewAdapter(listeDeCategories.getListeCategories()));
        }
        return view;
    }

//    protected void configureAdaptater(View view) {
//        if (view instanceof RecyclerView) {
//            Context context = view.getContext();
//            RecyclerView recyclerView = (RecyclerView) view;
//            if (mColumnCount <= 1) {
//                recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            } else {
//                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
//            }
//            recyclerView.setAdapter(new MyListCategoriesRecyclerViewAdapter(DummyContent.ITEMS));
//        }
//    }
}