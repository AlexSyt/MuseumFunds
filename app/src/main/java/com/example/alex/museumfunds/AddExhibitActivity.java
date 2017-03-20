package com.example.alex.museumfunds;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TableLayout;

public class AddExhibitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exhibit);
    }

    public void addAuthorBtnClicked(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View adOrganiserLayout = inflater.inflate(R.layout.add_author_ad, null);

        AlertDialog adAuthor = new AlertDialog.Builder(this)
                .setTitle("Add New Author")
                .setView(adOrganiserLayout)
                .setPositiveButton("Add", (dialog, id) -> {

                })
                .setNegativeButton("Cancel", null)
                .create();
        adAuthor.show();
    }

    public void addFundCatalogBtnClicked(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View adFundCatalogView = inflater.inflate(R.layout.add_fund_catalog_ad, null);
        final CheckBox cbNewFund = (CheckBox) adFundCatalogView.findViewById(R.id.new_fund_cb);
        final TableLayout tlNewFund = (TableLayout) adFundCatalogView.findViewById(R.id.new_fund_tl);

        cbNewFund.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                tlNewFund.setVisibility(View.VISIBLE);
            } else {
                tlNewFund.setVisibility(View.GONE);
            }
        });

        AlertDialog adFundCatalog = new AlertDialog.Builder(this)
                .setTitle("Add New Fund Catalog")
                .setView(adFundCatalogView)
                .setPositiveButton("Add", (dialog, id) -> {

                })
                .setNegativeButton("Cancel", null)
                .create();
        adFundCatalog.show();
    }

    public void addExhibitionBtnClicked(View view) {

    }
}
