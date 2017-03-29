package com.example.alex.museumfunds;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alex.museumfunds.db.DbHelper;
import com.example.alex.museumfunds.model.Exhibit;
import com.example.alex.museumfunds.model.ExhibitExhibition;
import com.example.alex.museumfunds.model.Exhibition;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;

import java.sql.SQLException;
import java.util.List;


public class ExhibitionsFragment extends Fragment {

    private static final String TAG = ExhibitionsFragment.class.getSimpleName();
    private DbHelper dbHelper;
    private PreparedQuery<Exhibit> exhibitsPq;

    public ExhibitionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exhibitions, container, false);
    }

    @Override
    public void onDestroy() {
        if (dbHelper != null) {
            OpenHelperManager.releaseHelper();
            dbHelper = null;
        }
        super.onDestroy();
    }

    private DbHelper getHelper() {
        if (dbHelper == null) {
            dbHelper = OpenHelperManager.getHelper(getActivity(), DbHelper.class);
        }
        return dbHelper;
    }

    private List<Exhibit> getExhibits(Exhibition exhibition) throws SQLException {
        if (exhibitsPq == null) {
            exhibitsPq = makeExhibitsPq();
        }
        exhibitsPq.setArgumentHolderValue(0, exhibition);
        Dao<Exhibit, Integer> dao = getHelper().getExhibitDao();
        return dao.query(exhibitsPq);
    }

    private PreparedQuery<Exhibit> makeExhibitsPq() throws SQLException {
        Dao<ExhibitExhibition, Integer> dao = getHelper().getExhibitExhibitionDao();
        QueryBuilder<ExhibitExhibition, Integer> exhibitExhibitionQb = dao.queryBuilder();

        exhibitExhibitionQb.selectColumns(ExhibitExhibition.EXHIBIT_ID);
        SelectArg selectArg = new SelectArg();
        exhibitExhibitionQb.where().eq(ExhibitExhibition.EXHIBITION_ID, selectArg);

        Dao<Exhibit, Integer> exhibitDao = getHelper().getExhibitDao();
        QueryBuilder<Exhibit, Integer> exhibitQb = exhibitDao.queryBuilder();

        exhibitQb.where().in("id", exhibitExhibitionQb);
        return exhibitQb.prepare();
    }
}
