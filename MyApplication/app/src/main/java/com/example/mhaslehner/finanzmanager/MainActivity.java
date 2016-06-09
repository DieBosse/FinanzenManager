package com.example.mhaslehner.finanzmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView img;
    private static SharedPreferences prefs = null;
    private static SharedPreferences.OnSharedPreferenceChangeListener listener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = (ImageView) findViewById(R.id.imageView);
        img.setImageResource(R.drawable.smiley_gruen);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals("verdienst")) {
                   // updateVerdienst();
                }
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.kategorienmenu)
        {
            startActivity(new Intent(this, KategorieVerwaltung.class));
        }
        if (id == R.id.verdienstmenu)
        {
            startActivity(new Intent(this, PreferenceActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void addAusgabe(View view) {
        Intent i = new Intent(getApplicationContext(), AddAusgabe.class);
        startActivity(i);
    }

    public void addEinnahme(View view) {
        Intent i = new Intent(getApplicationContext(), AddEinnahme.class);
        startActivity(i);
    }

    public void kategorieStatistik(View view) {
        Intent i = new Intent(getApplicationContext(), KategorieStatistik.class);
        startActivity(i);
    }

    public void wochenStatistik(View view) {
        Intent i = new Intent(getApplicationContext(), WochenStatistik.class);
        startActivity(i);
    }

    public void verbesserungen(View view) {
        Intent i = new Intent(getApplicationContext(), Verbesserungen.class);
        startActivity(i);
    }
}
