package com.wontanara.dictionnairedelanguedessignes.controller.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wontanara.dictionnairedelanguedessignes.R;

public abstract class BaseFragment extends Fragment {


//    ------ BASE METHODS ------

    protected abstract BaseFragment newInstance();
    protected abstract int getFragmentLayout();
    protected abstract void configureDesign(View view);
    protected abstract void findElements();


//    ------ METHODS OVERRIDE ------


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        this.findElements();
        this.configureDesign(view);
        return(view);
    }



    protected void replaceFragment(Fragment fragment, int layout){
//        assert getFragmentManager() != null;
        getFragmentManager().beginTransaction()
                .replace(layout, fragment)
                .addToBackStack(null) // Permet d'ajouter les fragments au backstack, -> si on fait retour on retourne au fragment précédent
                .commit();
    }


}