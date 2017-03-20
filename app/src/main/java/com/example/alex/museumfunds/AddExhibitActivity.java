package com.example.alex.museumfunds;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.alex.museumfunds.db.DbHelper;
import com.example.alex.museumfunds.model.Author;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class AddExhibitActivity extends AppCompatActivity {

    private static final String TAG = AddExhibitActivity.class.getSimpleName();
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exhibit);
    }

    @Override
    protected void onDestroy() {
        if (dbHelper != null) {
            OpenHelperManager.releaseHelper();
            dbHelper = null;
        }
        super.onDestroy();
    }

    private DbHelper getHelper() {
        if (dbHelper == null) {
            dbHelper = OpenHelperManager.getHelper(this, DbHelper.class);
        }
        return dbHelper;
    }

    public void addAuthorBtnClicked(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View adOrganiserLayout = inflater.inflate(R.layout.add_author_ad, null);

        EditText etName = (EditText) adOrganiserLayout.findViewById(R.id.author_name_et);
        EditText etDob = (EditText) adOrganiserLayout.findViewById(R.id.author_dob_et);
        EditText etCountry = (EditText) adOrganiserLayout.findViewById(R.id.author_country_et);

        AlertDialog adAuthor = new AlertDialog.Builder(this)
                .setTitle("Add New Author")
                .setView(adOrganiserLayout)
                .setPositiveButton("Add", (dialog, id) -> {
                    addAuthor(etName.getText().toString(), etDob.getText().toString(),
                            etCountry.getText().toString());
                })
                .setNegativeButton("Cancel", null)
                .create();
        adAuthor.show();
    }

    private void addAuthor(String name, String dob, String country) {
        if (name.trim().length() > 0 && dob.trim().length() > 0 && country.trim().length() > 0) {
            final Author author = new Author(name, dob, country);
            try {
                final Dao<Author, Integer> authorDao = getHelper().getAuthorDao();
                authorDao.create(author);
            } catch (SQLException e) {
                Log.e(TAG, "Unable to create author" + author, e);
            }
        } else {
            Toast.makeText(this, "Fields must be filled", Toast.LENGTH_SHORT).show();
        }
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
