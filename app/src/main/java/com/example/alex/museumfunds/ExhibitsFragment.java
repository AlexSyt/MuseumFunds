package com.example.alex.museumfunds;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    private List<Exhibit> exhibits = null;
    private ExhibitCardAdapter adapter;

    public ExhibitsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exhibits, container, false);

        RecyclerView exhibitsList = (RecyclerView) view.findViewById(R.id.exhibits_list);
        exhibitsList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        exhibitsList.setLayoutManager(layoutManager);

        loadExhibits();

        adapter = new ExhibitCardAdapter(exhibits, this);
        exhibitsList.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroy() {
        if (dbHelper != null) {
            OpenHelperManager.releaseHelper();
            dbHelper = null;
        }
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadExhibits();
        adapter.updateData(exhibits);
    }

    private void loadExhibits() {
        try {
            Dao<Exhibit, Integer> exhibitDao = getHelper().getExhibitDao();
            exhibits = exhibitDao.queryForAll();
        } catch (SQLException e) {
            Log.e(TAG, "Unable to load exhibits", e);
        }
    }

    private DbHelper getHelper() {
        if (dbHelper == null) {
            dbHelper = OpenHelperManager.getHelper(getActivity(), DbHelper.class);
        }
        return dbHelper;
    }

    public List<Exhibition> getExhibitions(Exhibit exhibit) {
        try {
            if (exhibitionsPq == null) {
                exhibitionsPq = makeExhibitionsPq();
            }
            exhibitionsPq.setArgumentHolderValue(0, exhibit);
            Dao<Exhibition, Integer> dao = getHelper().getExhibitionDao();
            return dao.query(exhibitionsPq);
        } catch (SQLException e) {
            Log.e(TAG, "Unable to load exhibitions", e);
        }
        return null;
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

class ExhibitCardAdapter extends RecyclerView.Adapter<ExhibitCardAdapter.ExhibitViewHolder> {

    private final List<Exhibit> exhibits;
    private final ExhibitsFragment fragment;

    public ExhibitCardAdapter(List<Exhibit> exhibits, ExhibitsFragment fragment) {
        this.exhibits = exhibits;
        this.fragment = fragment;
    }

    @Override
    public ExhibitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exhibit_card, parent, false);

        return new ExhibitViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExhibitViewHolder holder, int position) {
        Exhibit exhibit = exhibits.get(position);
        holder.tvExhibitName.setText(exhibit.getName());
        holder.tvCreationYear.setText(exhibit.getCreationYear());
        holder.tvAuthorName.setText(exhibit.getAuthor().getName());
        holder.tvAuthorCountry.setText(exhibit.getAuthor().getCountry());
        holder.tvAuthorDob.setText(exhibit.getAuthor().getDob());
        holder.tvFundCatalog.setText(exhibit.getFundCatalog().getName());
        holder.tvFund.setText(exhibit.getFundCatalog().getFund().getName());
        holder.tvFundDescription.setText(exhibit.getFundCatalog().getFund().getDescription());
        holder.tvExhibitions.setText(fragment.getExhibitions(exhibit).toString());
    }

    @Override
    public int getItemCount() {
        return exhibits.size();
    }

    public void updateData(List<Exhibit> exhibits) {
        this.exhibits.clear();
        this.exhibits.addAll(exhibits);
        notifyDataSetChanged();
    }

    static class ExhibitViewHolder extends RecyclerView.ViewHolder {

        final TextView tvExhibitName;
        final TextView tvCreationYear;
        final TextView tvAuthorName;
        final TextView tvAuthorCountry;
        final TextView tvAuthorDob;
        final TextView tvFundCatalog;
        final TextView tvFund;
        final TextView tvFundDescription;
        final TextView tvExhibitions;

        public ExhibitViewHolder(View itemView) {
            super(itemView);
            tvExhibitName = (TextView) itemView.findViewById(R.id.exhibit_name_tv);
            tvCreationYear = (TextView) itemView.findViewById(R.id.exhibit_creation_year_tv);
            tvAuthorName = (TextView) itemView.findViewById(R.id.author_name_tv);
            tvAuthorCountry = (TextView) itemView.findViewById(R.id.author_country_tv);
            tvAuthorDob = (TextView) itemView.findViewById(R.id.author_dob_tv);
            tvFundCatalog = (TextView) itemView.findViewById(R.id.fund_catalog_name_tv);
            tvFund = (TextView) itemView.findViewById(R.id.fund_name_tv);
            tvFundDescription = (TextView) itemView.findViewById(R.id.fund_description_tv);
            tvExhibitions = (TextView) itemView.findViewById(R.id.exhibit_exhibitions_tv);
        }
    }
}