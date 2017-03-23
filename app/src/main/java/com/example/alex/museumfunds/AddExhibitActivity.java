package com.example.alex.museumfunds;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.alex.museumfunds.db.DbHelper;
import com.example.alex.museumfunds.model.Author;
import com.example.alex.museumfunds.model.Fund;
import com.example.alex.museumfunds.model.FundCatalog;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class AddExhibitActivity extends AppCompatActivity {

    private static final String TAG = AddExhibitActivity.class.getSimpleName();
    private DbHelper dbHelper;
    private EditText etExhibitName;
    private EditText etCreationYear;
    private Spinner spAuthors;
    private Spinner spFundCatalogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exhibit);

        spAuthors = (Spinner) findViewById(R.id.exhibit_author_sp);
        spFundCatalogs = (Spinner) findViewById(R.id.fund_catalog_sp);
        etExhibitName = (EditText) findViewById(R.id.exhibit_name_et);
        etCreationYear = (EditText) findViewById(R.id.exhibit_creation_year_et);

        setSpAuthorsAdapter();
        setSpFundCatalogsAdapter();
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


    private List<Author> authors;
    private CustomAdapter authorsAdapter;

    private void setSpAuthorsAdapter() {
        try {
            final Dao<Author, Integer> authorDao = getHelper().getAuthorDao();
            authors = authorDao.queryForAll();
        } catch (SQLException e) {
            Log.e(TAG, "Unable to load authors from DB", e);
        }

        authorsAdapter = new CustomAdapter(this, android.R.layout.simple_spinner_item, authors);
        authorsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spAuthors.setAdapter(authorsAdapter);
    }

    public void addAuthorBtnClicked(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View adAuthorLayout = inflater.inflate(R.layout.add_author_ad, null);

        EditText etName = (EditText) adAuthorLayout.findViewById(R.id.author_name_et);
        EditText etDob = (EditText) adAuthorLayout.findViewById(R.id.author_dob_et);
        EditText etCountry = (EditText) adAuthorLayout.findViewById(R.id.author_country_et);

        AlertDialog adAuthor = new AlertDialog.Builder(this)
                .setTitle("Add New Author")
                .setView(adAuthorLayout)
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
                authors.add(author);
                authorsAdapter.notifyDataSetChanged();
                spAuthors.setSelection(authorsAdapter.getCount());
            } catch (SQLException e) {
                Log.e(TAG, "Unable to create author", e);
            }
        } else {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }
    }


    private List<FundCatalog> fundCatalogs;
    private CustomAdapter fundCatalogsAdapter;

    private void setSpFundCatalogsAdapter() {
        try {
            final Dao<FundCatalog, Integer> fundCatalogDao = getHelper().getFundCatalogDao();
            fundCatalogs = fundCatalogDao.queryForAll();
        } catch (SQLException e) {
            Log.e(TAG, "Unable to load fund catalogs from DB", e);
        }

        fundCatalogsAdapter = new CustomAdapter(this, android.R.layout.simple_spinner_item, fundCatalogs);
        fundCatalogsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spFundCatalogs.setAdapter(fundCatalogsAdapter);
    }

    public void addFundCatalogBtnClicked(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View adFundCatalogView = inflater.inflate(R.layout.add_fund_catalog_ad, null);

        final CheckBox cbNewFund = (CheckBox) adFundCatalogView.findViewById(R.id.new_fund_cb);
        final TableLayout tlNewFund = (TableLayout) adFundCatalogView.findViewById(R.id.new_fund_tl);
        final Spinner spFunds = (Spinner) adFundCatalogView.findViewById(R.id.funds_sp);
        EditText etName = (EditText) adFundCatalogView.findViewById(R.id.fund_catalog_name_et);
        EditText etFundName = (EditText) adFundCatalogView.findViewById(R.id.fund_name_et);
        EditText etDescr = (EditText) adFundCatalogView.findViewById(R.id.fund_description_et);

        final boolean[] newFund = new boolean[1];

        cbNewFund.setOnCheckedChangeListener((buttonView, isChecked) -> {
            newFund[0] = isChecked;
            if (isChecked) {
                tlNewFund.setVisibility(View.VISIBLE);
                spFunds.setEnabled(false);
            } else {
                tlNewFund.setVisibility(View.GONE);
                spFunds.setEnabled(true);
            }
        });

        setSpFundsAdapter(spFunds);

        AlertDialog adFundCatalog = new AlertDialog.Builder(this)
                .setTitle("Add New Fund Catalog")
                .setView(adFundCatalogView)
                .setPositiveButton("Add", (dialog, id) -> {
                    String fcName = etName.getText().toString();
                    if (newFund[0]) {
                        addFundCatalogWithNewFund(fcName,
                                etFundName.getText().toString(), etDescr.getText().toString());
                    } else {
                        if (fcName.trim().length() > 0 && spFunds.getCount() > 0) {
                            createFundCatalog(new FundCatalog(fcName, (Fund) spFunds.getSelectedItem()));
                        } else {
                            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        adFundCatalog.show();
    }

    private void setSpFundsAdapter(Spinner spFunds) {
        List<Fund> funds = null;

        try {
            final Dao<Fund, Integer> fundDao = getHelper().getFundDao();
            funds = fundDao.queryForAll();
        } catch (SQLException e) {
            Log.e(TAG, "Unable to load funds from DB", e);
        }

        CustomAdapter fundsAdapter = new CustomAdapter(this, android.R.layout.simple_spinner_item, funds);
        fundsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spFunds.setAdapter(fundsAdapter);
    }

    private void addFundCatalogWithNewFund(String fcName, String fName, String fDescr) {
        if (fName.trim().length() > 0 && fDescr.trim().length() > 0 && fcName.trim().length() > 0) {
            Fund addedFund = new Fund(fName, fDescr);
            createFund(addedFund);

            createFundCatalog(new FundCatalog(fcName, addedFund));
        } else {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }
    }

    private void createFund(Fund addedFund) {
        try {
            final Dao<Fund, Integer> fundDao = getHelper().getFundDao();
            fundDao.create(addedFund);
        } catch (SQLException e) {
            Log.e(TAG, "Unable to create fund", e);
        }
    }

    private void createFundCatalog(FundCatalog newCatalog) {
        try {
            final Dao<FundCatalog, Integer> fundCatalogDao = getHelper().getFundCatalogDao();
            fundCatalogDao.create(newCatalog);
        } catch (SQLException e) {
            Log.e(TAG, "Unable to create fund catalog", e);
        }
        fundCatalogs.add(newCatalog);
        fundCatalogsAdapter.notifyDataSetChanged();
        spFundCatalogs.setSelection(fundCatalogsAdapter.getCount());
    }


    public void addExhibitionBtnClicked(View view) {

    }

    public void resetBtnClicked(View view) {
        etExhibitName.setText("");
        etCreationYear.setText("");
        spAuthors.setSelection(0);
        spFundCatalogs.setSelection(0);
    }

    public void submitBtnClicked(View view) {

    }
}
