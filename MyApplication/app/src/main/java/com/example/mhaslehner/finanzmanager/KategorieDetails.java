package com.example.mhaslehner.finanzmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by HP on 16.06.2016.
 */
public class KategorieDetails extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategorie_details);

        showKategorieDetails();
    }

    private void showKategorieDetails() {
        String name = getIntent().getStringExtra("nameKategorie");
        String beschreibung = getIntent().getStringExtra("beschreibungKategorie");

        TextView textName = (TextView) findViewById(R.id.textViewKategorieDetailName);
        TextView textBeschreibung = (TextView) findViewById(R.id.textViewKategorieDetailBeschreibung);
        textName.setText(name);
        textBeschreibung.setText(beschreibung);
    }


}
