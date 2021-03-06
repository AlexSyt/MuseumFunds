package com.example.alex.museumfunds;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
import java.text.SimpleDateFormat;
import java.util.List;


public class ExhibitionsFragment extends Fragment {

    private static final String TAG = ExhibitionsFragment.class.getSimpleName();
    private DbHelper dbHelper;
    private PreparedQuery<Exhibit> exhibitsPq;
    private List<Exhibition> exhibitions = null;
    private ExhibitionCardAdapter adapter;

    public ExhibitionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exhibitions, container, false);

        RecyclerView exhibitionsList = (RecyclerView) view.findViewById(R.id.exhibitions_list);
        exhibitionsList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        exhibitionsList.setLayoutManager(layoutManager);

        loadExhibitions();

        adapter = new ExhibitionCardAdapter(exhibitions, this);
        exhibitionsList.setAdapter(adapter);

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
        loadExhibitions();
        adapter.updateData(exhibitions);
    }

    private void loadExhibitions() {
        try {
            Dao<Exhibition, Integer> exhibitionDao = getHelper().getExhibitionDao();
            exhibitions = exhibitionDao.queryForAll();
        } catch (SQLException e) {
            Log.e(TAG, "Unable to load exhibitions", e);
        }
    }

    private DbHelper getHelper() {
        if (dbHelper == null) {
            dbHelper = OpenHelperManager.getHelper(getActivity(), DbHelper.class);
        }
        return dbHelper;
    }

    /**
     * This is the method for obtaining all the exhibits of the exhibition.
     *
     * @param exhibition exhibition, the exhibits of which you need to get.
     * @return list of exhibits if exists, otherwise null.
     */
    public List<Exhibit> getExhibits(Exhibition exhibition) {
        try {
            if (exhibitsPq == null) {
                exhibitsPq = makeExhibitsPq();
            }
            exhibitsPq.setArgumentHolderValue(0, exhibition);
            Dao<Exhibit, Integer> dao = getHelper().getExhibitDao();
            return dao.query(exhibitsPq);
        } catch (SQLException e) {
            Log.e(TAG, "Unable to load exhibits", e);
        }
        return null;
    }

    /**
     * This method prepares a query for the receipt of exhibits.
     *
     * @return prepared query for getting the exhibits.
     * @throws SQLException if we can not get DAO or problems with query builder.
     */
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

class ExhibitionCardAdapter extends RecyclerView.Adapter<ExhibitionCardAdapter.ExhibitionViewHolder> {

    private final List<Exhibition> exhibitions;
    private final ExhibitionsFragment fragment;

    public ExhibitionCardAdapter(List<Exhibition> exhibitions, ExhibitionsFragment fragment) {
        this.exhibitions = exhibitions;
        this.fragment = fragment;
    }

    @Override
    public ExhibitionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exhibition_card, parent, false);

        ImageButton print = (ImageButton) itemView.findViewById(R.id.print_exhibition_ib);
        print.setOnClickListener(v ->
                Toast.makeText(fragment.getContext(), "In Development", Toast.LENGTH_SHORT).show());

        return new ExhibitionCardAdapter.ExhibitionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExhibitionViewHolder holder, int position) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Exhibition exhibition = exhibitions.get(position);
        holder.tvExhibitionName.setText(exhibition.getName());
        holder.tvExhibitionStart.setText(sdf.format(exhibition.getStartDate()));
        holder.tvExhibitionEnd.setText(sdf.format(exhibition.getEndDate()));
        holder.tvOrganiserName.setText(exhibition.getOrganiser().getName());
        holder.tvOrganiserAddress.setText(exhibition.getOrganiser().getAddress());
        holder.tvOrganiserNumber.setText(exhibition.getOrganiser().getPhoneNumber());
        holder.tvExhibits.setText(fragment.getExhibits(exhibition).toString());
    }

    @Override
    public int getItemCount() {
        return exhibitions.size();
    }

    public void updateData(List<Exhibition> exhibitions) {
        this.exhibitions.clear();
        this.exhibitions.addAll(exhibitions);
        notifyDataSetChanged();
    }

    static class ExhibitionViewHolder extends RecyclerView.ViewHolder {

        final TextView tvExhibitionName;
        final TextView tvExhibitionStart;
        final TextView tvExhibitionEnd;
        final TextView tvOrganiserName;
        final TextView tvOrganiserAddress;
        final TextView tvOrganiserNumber;
        final TextView tvExhibits;

        public ExhibitionViewHolder(View itemView) {
            super(itemView);
            tvExhibitionName = (TextView) itemView.findViewById(R.id.exhibition_name_tv);
            tvExhibitionStart = (TextView) itemView.findViewById(R.id.exhibition_start_tv);
            tvExhibitionEnd = (TextView) itemView.findViewById(R.id.exhibition_end_tv);
            tvOrganiserName = (TextView) itemView.findViewById(R.id.organiser_name_tv);
            tvOrganiserAddress = (TextView) itemView.findViewById(R.id.organiser_address_tv);
            tvOrganiserNumber = (TextView) itemView.findViewById(R.id.organiser_number_tv);
            tvExhibits = (TextView) itemView.findViewById(R.id.exhibition_exhibits_tv);
        }
    }
}
