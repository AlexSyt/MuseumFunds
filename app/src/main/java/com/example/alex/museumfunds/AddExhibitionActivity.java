package com.example.alex.museumfunds;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.museumfunds.db.DbHelper;
import com.example.alex.museumfunds.model.Exhibit;
import com.example.alex.museumfunds.model.ExhibitExhibition;
import com.example.alex.museumfunds.model.Exhibition;
import com.example.alex.museumfunds.model.Organiser;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddExhibitionActivity extends AppCompatActivity {

    private static final String TAG = AddExhibitionActivity.class.getSimpleName();
    private final ArrayList<Exhibit> exhibits = new ArrayList<>();
    private DbHelper dbHelper;
    private EditText etExhibitionName;
    private EditText etDuration;
    private Spinner spOrganisers;
    private Spinner spExhibits;
    private TextView tvExhibits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exhibition);

        etExhibitionName = (EditText) findViewById(R.id.exhibition_name_et);
        etDuration = (EditText) findViewById(R.id.exhibition_duration_et);
        spOrganisers = (Spinner) findViewById(R.id.exhibition_organiser_sp);
        spExhibits = (Spinner) findViewById(R.id.exhibits_sp);
        tvExhibits = (TextView) findViewById(R.id.exhibits_tv);

        setSpOrganisersAdapter();
        setSpExhibitsAdapter();
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


    private void setSpExhibitsAdapter() {
        List<Exhibit> exhibits = null;

        try {
            final Dao<Exhibit, Integer> exhibitDao = getHelper().getExhibitDao();
            exhibits = exhibitDao.queryForAll();
        } catch (SQLException e) {
            Log.e(TAG, "Unable to load exhibits from DB", e);
        }

        CustomAdapter exhibitsAd = new CustomAdapter(this, exhibits);
        exhibitsAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spExhibits.setAdapter(exhibitsAd);
    }


    private List<Organiser> organisers;
    private CustomAdapter organisersAdaptor;

    private void setSpOrganisersAdapter() {
        try {
            final Dao<Organiser, Integer> organiserDao = getHelper().getOrganiserDao();
            organisers = organiserDao.queryForAll();
        } catch (SQLException e) {
            Log.e(TAG, "Unable to load organisers from DB", e);
        }

        organisersAdaptor = new CustomAdapter(this, organisers);
        organisersAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spOrganisers.setAdapter(organisersAdaptor);
    }

    public void addOrganiserBtnClicked(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View adOrganiserLayout = inflater.inflate(R.layout.add_organiser_ad, null);

        EditText etName = (EditText) adOrganiserLayout.findViewById(R.id.organiser_name_et);
        EditText etAddress = (EditText) adOrganiserLayout.findViewById(R.id.organiser_address_et);
        EditText etNumber = (EditText) adOrganiserLayout.findViewById(R.id.organiser_number_et);

        AlertDialog adOrganiser = new AlertDialog.Builder(this)
                .setTitle("Add New Organiser")
                .setView(adOrganiserLayout)
                .setPositiveButton("Add", (dialog, id) -> {
                    addOrganiser(etName.getText().toString(), etAddress.getText().toString(),
                            etNumber.getText().toString());
                })
                .setNegativeButton("Cancel", null)
                .create();
        adOrganiser.show();
    }

    private void addOrganiser(String name, String address, String number) {
        if (name.trim().length() > 0 && address.trim().length() > 0 && number.trim().length() > 0) {
            final Organiser org = new Organiser(name, address, number);
            try {
                final Dao<Organiser, Integer> organiserDao = getHelper().getOrganiserDao();
                organiserDao.create(org);
                organisers.add(org);
                organisersAdaptor.notifyDataSetChanged();
                spOrganisers.setSelection(organisersAdaptor.getCount());
            } catch (SQLException e) {
                Log.e(TAG, "Unable to create organiser", e);
            }
        } else {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }
    }


    public void addExhibitBtnClicked(View view) {
        Exhibit exhibit = (Exhibit) spExhibits.getSelectedItem();
        if (!exhibits.contains(exhibit)) {
            exhibits.add(exhibit);
        }
        tvExhibits.setText(exhibits.toString());
    }

    public void resetBtnClicked(View view) {
        etExhibitionName.setText("");
        etDuration.setText("");
        spOrganisers.setSelection(0);
        spExhibits.setSelection(0);
        exhibits.clear();
        tvExhibits.setText(getText(R.string.exhibits));
    }

    public void submitBtnClicked(View view) {
        String name = etExhibitionName.getText().toString();
        String duration = etDuration.getText().toString();
        boolean isFieldsFilled = name.trim().length() > 0 && duration.trim().length() > 0;

        if (isFieldsFilled && spOrganisers.getCount() > 0) {
            String[] dur = duration.split("-");
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            sdf.setLenient(false);
            final Organiser org = (Organiser) spOrganisers.getSelectedItem();
            try {
                Date start = sdf.parse(dur[0]);
                Date end = sdf.parse(dur[1]);
                Exhibition exhibition = new Exhibition(name, org, start, end);

                createExhibition(exhibition);
                if (!exhibits.isEmpty()) {
                    createExhibitionExhibits(exhibition);
                }
                resetBtnClicked(view);
            } catch (ParseException e) {
                Toast.makeText(this, "Invalid duration", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Unable to parse date", e);
            }
        } else {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }
    }

    private void createExhibition(Exhibition exhibition) {
        try {
            final Dao<Exhibition, Integer> exhibitionDao = getHelper().getExhibitionDao();
            exhibitionDao.create(exhibition);
        } catch (SQLException e) {
            Log.e(TAG, "Unable to create exhibition", e);
        }
    }

    private void createExhibitionExhibits(Exhibition exhibition) {
        try {
            final Dao<ExhibitExhibition, Integer> dao = getHelper().getExhibitExhibitionDao();
            for (Exhibit exhibit : exhibits) {
                dao.create(new ExhibitExhibition(exhibit, exhibition));
            }
        } catch (SQLException e) {
            Log.e(TAG, "Unable to create exhibition exhibits", e);
        }
    }
}
