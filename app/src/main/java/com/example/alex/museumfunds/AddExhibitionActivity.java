package com.example.alex.museumfunds;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

public class AddExhibitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exhibition);
    }

    public void addOrganiserBtnClicked(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View adOrganiserLayout = inflater.inflate(R.layout.add_organiser_ad, null);

        AlertDialog adOrganiser = new AlertDialog.Builder(this)
                .setTitle("Add New Organiser")
                .setView(adOrganiserLayout)
                .setPositiveButton("Add", (dialog, id) -> {

                })
                .setNegativeButton("Cancel", null)
                .create();
        adOrganiser.show();
    }

    public void addExhibitBtnClicked(View view) {

    }
}
