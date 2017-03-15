package com.example.alex.museumfunds.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.alex.museumfunds.model.Author;
import com.example.alex.museumfunds.model.Exhibit;
import com.example.alex.museumfunds.model.ExhibitExhibition;
import com.example.alex.museumfunds.model.Exhibition;
import com.example.alex.museumfunds.model.Fund;
import com.example.alex.museumfunds.model.FundCatalog;
import com.example.alex.museumfunds.model.Organiser;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;


public class DbHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DbHelper.class.getSimpleName();
    private static final String DB_NAME = "museum_funds.db";
    private static final int DB_VERSION = 1;

    private Dao<Author, Integer> authorDao;
    private Dao<Exhibit, Integer> exhibitDao;
    private Dao<ExhibitExhibition, Integer> exhibitExhibitionDao;
    private Dao<Exhibition, Integer> exhibitionDao;
    private Dao<Fund, Integer> fundDao;
    private Dao<FundCatalog, Integer> fundCatalogDao;
    private Dao<Organiser, Integer> organiserDao;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Author.class);
            TableUtils.createTable(connectionSource, Exhibit.class);
            TableUtils.createTable(connectionSource, ExhibitExhibition.class);
            TableUtils.createTable(connectionSource, Exhibition.class);
            TableUtils.createTable(connectionSource, Fund.class);
            TableUtils.createTable(connectionSource, FundCatalog.class);
            TableUtils.createTable(connectionSource, Organiser.class);
        } catch (SQLException e) {
            Log.e(TAG, "Unable to create DB", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource, Author.class, true);
            TableUtils.dropTable(connectionSource, Exhibit.class, true);
            TableUtils.dropTable(connectionSource, ExhibitExhibition.class, true);
            TableUtils.dropTable(connectionSource, Exhibition.class, true);
            TableUtils.dropTable(connectionSource, Fund.class, true);
            TableUtils.dropTable(connectionSource, FundCatalog.class, true);
            TableUtils.dropTable(connectionSource, Organiser.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            Log.e(TAG, "Unable to upgrade db from version " + i + " to new " + i1, e);
        }
    }

    public Dao<Author, Integer> getAuthorDao() throws SQLException {
        if (authorDao == null) {
            authorDao = getDao(Author.class);
        }
        return authorDao;
    }

    public Dao<Exhibit, Integer> getExhibitDao() throws SQLException {
        if (exhibitDao == null) {
            exhibitDao = getDao(Exhibit.class);
        }
        return exhibitDao;
    }

    public Dao<ExhibitExhibition, Integer> getExhibitExhibitionDao() throws SQLException {
        if (exhibitExhibitionDao == null) {
            exhibitExhibitionDao = getDao(ExhibitExhibition.class);
        }
        return exhibitExhibitionDao;
    }

    public Dao<Exhibition, Integer> getExhibitionDao() throws SQLException {
        if (exhibitionDao == null) {
            exhibitionDao = getDao(Exhibition.class);
        }
        return exhibitionDao;
    }

    public Dao<Fund, Integer> getFundDao() throws SQLException {
        if (fundDao == null) {
            fundDao = getDao(Fund.class);
        }
        return fundDao;
    }

    public Dao<FundCatalog, Integer> getFundCatalogDao() throws SQLException {
        if (fundCatalogDao == null) {
            fundCatalogDao = getDao(FundCatalog.class);
        }
        return fundCatalogDao;
    }

    public Dao<Organiser, Integer> getOrganiserDao() throws SQLException {
        if (organiserDao == null) {
            organiserDao = getDao(Organiser.class);
        }
        return organiserDao;
    }

    @Override
    public void close() {
        authorDao = null;
        exhibitDao = null;
        exhibitExhibitionDao = null;
        exhibitionDao = null;
        fundDao = null;
        fundCatalogDao = null;
        organiserDao = null;
        super.close();
    }
}
