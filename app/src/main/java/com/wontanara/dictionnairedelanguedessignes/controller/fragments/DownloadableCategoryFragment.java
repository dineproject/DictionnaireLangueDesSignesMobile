package com.wontanara.dictionnairedelanguedessignes.controller.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.wontanara.dictionnairedelanguedessignes.R;
import com.wontanara.dictionnairedelanguedessignes.model.DownloadableCategory;
import com.wontanara.dictionnairedelanguedessignes.utils.ItemClickSupport;
import com.wontanara.dictionnairedelanguedessignes.view.DownloadableCategoryViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;

public class DownloadableCategoryFragment extends BaseFragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private ArrayList<DownloadableCategory> mDownloadableCategoryList;

    protected RecyclerView mRecyclerView;
    protected DownloadableCategoryViewAdapter mAdapter;

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

            this.mDownloadableCategoryList = new ArrayList<DownloadableCategory>();
            mAdapter = new DownloadableCategoryViewAdapter(this.mDownloadableCategoryList);
            mRecyclerView.setAdapter(this.mAdapter);
        }
    }

    protected void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(mRecyclerView, R.layout.fragment_categories_in_list)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                    }
                });
    }

    private void getCategories(){
        RequestQueue queue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()).getApplicationContext());
        String url = "http://ea-perso.ovh/api/category";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0 ; i < response.length() ; i++) {
                    JSONObject obj = null;
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
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Impossible de récupérer les catégories", Toast.LENGTH_LONG).show();
            }
        });

        queue.add(jsonArrayRequest);
    }
}
