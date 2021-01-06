package com.wontanara.dictionnairedelanguedessignes.controller.fragments;

import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.controller.activities.CategoriesActivity;
import com.wontanara.dictionnairedelanguedessignes.model.Categorie;
import com.wontanara.dictionnairedelanguedessignes.model.CategoriesListe;
import com.wontanara.dictionnairedelanguedessignes.model.Mot;
import com.wontanara.dictionnairedelanguedessignes.utils.ItemClickSupport;
import com.wontanara.dictionnairedelanguedessignes.view.MyCategorieRecyclerViewAdapter;
import com.wontanara.dictionnairedelanguedessignes.view.MyListCategoriesRecyclerViewAdapter;


public class CategorieFragment extends BaseFragment {


    private static final String ARG_ID_CATEGORIE = "id-categorie";
    private int mIdCategorie;
    private Toolbar mToolbar;
    private Categorie mCategorie;
    private RecyclerView mRecyclerView;
    private MyCategorieRecyclerViewAdapter mAdapter;
    private MotFragment mMotFragment;


    public CategorieFragment() {
        // Required empty public constructor
    }

//    ------ BASE METHODS ------

    @Override
    protected BaseFragment newInstance() {
        return new CategorieFragment();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_categorie;
    }

    @Override
    protected void configureDesign(View view) {
        this.mToolbar.setTitle(this.mCategorie.getNom());
        this.configureRecyclerView(view);
        this.configureOnClickRecyclerView();
    }

    @Override
    protected void findElements(View view) {
        this.mToolbar = ((CategoriesActivity) getActivity()).getToolbar();

        // Temporaire
        this.mCategorie = (new CategoriesListe()).getListeCategories().get(mIdCategorie - 1);

    }

//    ------ OVERRIDE METHODS ------
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.mIdCategorie = getArguments().getInt(ARG_ID_CATEGORIE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(ARG_ID_CATEGORIE, this.mIdCategorie);
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

            mAdapter = new MyCategorieRecyclerViewAdapter(mCategorie.getListeMots());
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
                        bundle.putInt("id-categorie", mIdCategorie);
                        mMotFragment.setArguments(bundle);

                        replaceFragment(mMotFragment, R.id.list_categories_frame_layout);

                    }
                });
    }

}