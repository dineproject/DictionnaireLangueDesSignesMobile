package com.wontanara.dictionnairedelanguedessignes.controller.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wontanara.dictionnairedelanguedessignes.R;


public class DictionnaireFragment extends BaseFragment {

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
        return R.layout.fragment_dictionnaire;
    }

    @Override
    protected void configureDesign(View view) {

    }

    @Override
    protected void findElements(View view) {

    }


}