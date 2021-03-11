package com.wontanara.dictionnairedelanguedessignes.controller.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.wontanara.dictionnairedelanguedessignes.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
    }
}
