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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.model.Category;
import com.wontanara.dictionnairedelanguedessignes.model.CategoryViewModel;
import com.wontanara.dictionnairedelanguedessignes.model.DownloadableCategory;
import com.wontanara.dictionnairedelanguedessignes.utils.ItemClickSupport;
import com.wontanara.dictionnairedelanguedessignes.view.DownloadableCategoryViewAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;

public class DownloadableCategoryFragment extends BaseFragment implements DownloadCategoryDialogFragment.DownloadCategoryDialogListener {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private ArrayList<DownloadableCategory> mDownloadableCategoryList;

    protected RecyclerView mRecyclerView;
    protected DownloadableCategoryViewAdapter mAdapter;

    private CategoryViewModel mCategoryViewModel;

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
        this.getCategories();
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

            this.mDownloadableCategoryList = new ArrayList<>();
            mAdapter = new DownloadableCategoryViewAdapter(this.mDownloadableCategoryList);
            mRecyclerView.setAdapter(this.mAdapter);
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

    private void getCategories(){
        RequestQueue queue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()).getApplicationContext());
        String url = "http://ea-perso.ovh/api/category";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for (int i = 0 ; i < response.length() ; i++) {
                JSONObject obj;
                try {
                    obj = response.getJSONObject(i);int id = obj.getInt("id");
                    String name = obj.getString("name");
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'", Locale.FRANCE);
                    GregorianCalendar updated_at = new GregorianCalendar();
                    updated_at.setTime(Objects.requireNonNull(dateFormat.parse(obj.getString("updated_at"))));
                    int word_count = obj.getInt("word_count");
                    DownloadableCategory category = new DownloadableCategory(id, name, updated_at, word_count);
                    mDownloadableCategoryList.add(category);
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
            }
            mAdapter.notifyDataSetChanged();
        }, error -> Toast.makeText(getContext(), "Impossible de récupérer les catégories", Toast.LENGTH_LONG).show());

        queue.add(jsonArrayRequest);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, DownloadableCategory downloadableCategory) {
        Category category = new Category(downloadableCategory.getId(), downloadableCategory.getName(), downloadableCategory.getUpdated_at());
        mCategoryViewModel.insert(category);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog, DownloadableCategory downloadableCategory) {

    }
}
