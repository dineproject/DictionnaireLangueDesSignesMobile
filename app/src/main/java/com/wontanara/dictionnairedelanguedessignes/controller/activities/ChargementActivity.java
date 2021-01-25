package com.wontanara.dictionnairedelanguedessignes.controller.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.wontanara.dictionnairedelanguedessignes.R;

public class ChargementActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chargement);

        // temps du chargement
        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(ChargementActivity.this, MainActivity.class));
                finish();
            }
        }, secondsDelayed * 1000);
    }
}