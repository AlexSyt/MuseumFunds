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
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.museumfunds.db.DbHelper;
import com.example.alex.museumfunds.model.Author;
import com.example.alex.museumfunds.model.Exhibit;
import com.example.alex.museumfunds.model.ExhibitExhibition;
import com.example.alex.museumfunds.model.Exhibition;
import com.example.alex.museumfunds.model.Fund;
import com.example.alex.museumfunds.model.FundCatalog;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddExhibitActivity extends AppCompatActivity {

    private static final String TAG = AddExhibitActivity.class.getSimpleName();
    private final ArrayList<Exhibition> exhibitions = new ArrayList<>();
    private DbHelper dbHelper;
    private EditText etExhibitName;
    private EditText etCreationYear;
    private Spinner spAuthors;
    private Spinner spFundCatalogs;
    private Spinner spExhibitions;
    private TextView tvExhibitions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exhibit);

        etExhibitName = (EditText) findViewById(R.id.exhibit_name_et);
        etCreationYear = (EditText) findViewById(R.id.exhibit_creation_year_et);
        spAuthors = (Spinner) findViewById(R.id.exhibit_author_sp);
        spFundCatalogs = (Spinner) findViewById(R.id.fund_catalog_sp);
        spExhibitions = (Spinner) findViewById(R.id.exhibitions_sp);
        tvExhibitions = (TextView) findViewById(R.id.exhibitions_tv);

        setSpAuthorsAdapter();
        setSpFundCatalogsAdapter();
        setSpExhibitionsAdapter();
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


    private void setSpExhibitionsAdapter() {
        List<Exhibition> exhibitions = null;

        try {
            final Dao<Exhibition, Integer> exhibitionDao = getHelper().getExhibitionDao();
            exhibitions = exhibitionDao.queryForAll();
        } catch (SQLException e) {
            Log.e(TAG, "Unable to load exhibitions from DB", e);
        }

        CustomAdapter exhbAd = new CustomAdapter(this, exhibitions);
        exhbAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spExhibitions.setAdapter(exhbAd);
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

        authorsAdapter = new CustomAdapter(this, authors);
        authorsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spAuthors.setAdapter(authorsAdapter);
    }

    /**
     * This method creates an alert dialog for adding a new author.
     *
     * @param view pressed button.
     */
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

    /**
     * This method checks whether the fields are filled correctly.
     * If all is well, it saves the new author in the database.
     * Otherwise the user is notified that all fields must be filled.
     *
     * @param name    author name.
     * @param dob     author dob.
     * @param country author country.
     */
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

        fundCatalogsAdapter = new CustomAdapter(this, fundCatalogs);
        fundCatalogsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spFundCatalogs.setAdapter(fundCatalogsAdapter);
    }

    /**
     * This is the method for displaying the alert dialog for adding a fund catalog (and fund,
     * if necessary). The addition of a new fund is regulated by the checkbox. The method partially
     * checks the filling of fields. If the fields are not filled the user is notified that all
     * fields must be filled.
     *
     * @param view pressed button.
     */
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

        CustomAdapter fundsAdapter = new CustomAdapter(this, funds);
        fundsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spFunds.setAdapter(fundsAdapter);
    }

    /**
     * This method checks whether the fields are filled correctly.
     * If all is well, it saves the new fund catalog and fund in the database.
     * Otherwise the user is notified that all fields must be filled.
     *
     * @param fcName fund catalog name.
     * @param fName  fund name.
     * @param fDescr fund description.
     */
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
        Exhibition exhibition = (Exhibition) spExhibitions.getSelectedItem();
        if (!exhibitions.contains(exhibition)) {
            exhibitions.add(exhibition);
        }
        tvExhibitions.setText(exhibitions.toString());
    }

    public void resetBtnClicked(View view) {
        etExhibitName.setText("");
        etCreationYear.setText("");
        spAuthors.setSelection(0);
        spFundCatalogs.setSelection(0);
        spExhibitions.setSelection(0);
        exhibitions.clear();
        tvExhibitions.setText(getString(R.string.exhibitions));
    }

    /**
     * If all necessary for exhibit fields are filled, then the exhibit is stored in the database,
     * otherwise the user is notified that all fields must be filled.
     *
     * @param view pressed button.
     */
    public void submitBtnClicked(View view) {
        String name = etExhibitName.getText().toString();
        String creationYear = etCreationYear.getText().toString();
        boolean isFieldsFilled = name.trim().length() > 0 && creationYear.trim().length() > 0;

        if (isFieldsFilled && spAuthors.getCount() > 0 && spFundCatalogs.getCount() > 0) {
            final Author author = (Author) spAuthors.getSelectedItem();
            final FundCatalog catalog = (FundCatalog) spFundCatalogs.getSelectedItem();
            final Exhibit exhibit = new Exhibit(name, creationYear, author, catalog);

            createExhibit(exhibit);

            if (!exhibitions.isEmpty()) {
                createExhibitExhibitions(exhibit);
            }

            resetBtnClicked(view);
        } else {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }
    }

    private void createExhibit(Exhibit exhibit) {
        try {
            final Dao<Exhibit, Integer> exhibitDao = getHelper().getExhibitDao();
            exhibitDao.create(exhibit);
        } catch (SQLException e) {
            Log.e(TAG, "Unable to create exhibit", e);
        }
    }

    private void createExhibitExhibitions(Exhibit exhibit) {
        try {
            final Dao<ExhibitExhibition, Integer> dao = getHelper().getExhibitExhibitionDao();
            for (Exhibition exhibition : exhibitions) {
                dao.create(new ExhibitExhibition(exhibit, exhibition));
            }
        } catch (SQLException e) {
            Log.e(TAG, "Unable to create exhibit exhibitions", e);
        }
    }
}
