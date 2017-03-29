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


public class ExhibitsFragment extends Fragment {

    private static final String TAG = ExhibitsFragment.class.getSimpleName();
    private DbHelper dbHelper;
    private PreparedQuery<Exhibition> exhibitionsPq;

    public ExhibitsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exhibits, container, false);
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

    private List<Exhibition> getExhibitions(Exhibit exhibit) throws SQLException {
        if (exhibitionsPq == null) {
            exhibitionsPq = makeExhibitionsPq();
        }
        exhibitionsPq.setArgumentHolderValue(0, exhibit);
        Dao<Exhibition, Integer> dao = getHelper().getExhibitionDao();
        return dao.query(exhibitionsPq);
    }

    private PreparedQuery<Exhibition> makeExhibitionsPq() throws SQLException {
        Dao<ExhibitExhibition, Integer> dao = getHelper().getExhibitExhibitionDao();
        QueryBuilder<ExhibitExhibition, Integer> exhibitExhibitionQb = dao.queryBuilder();

        exhibitExhibitionQb.selectColumns(ExhibitExhibition.EXHIBITION_ID);
        SelectArg selectArg = new SelectArg();
        exhibitExhibitionQb.where().eq(ExhibitExhibition.EXHIBIT_ID, selectArg);

        Dao<Exhibition, Integer> exhibitionDao = getHelper().getExhibitionDao();
        QueryBuilder<Exhibition, Integer> exhibitionQb = exhibitionDao.queryBuilder();

        exhibitionQb.where().in("id", exhibitExhibitionQb);
        return exhibitionQb.prepare();
    }
}
