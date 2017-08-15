package com.segeval.safedrive.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.segeval.safedrive.model.Managers.SqlManager;

import java.util.List;


public class ModelSql {


    SqlHelper sqlHelper = new SqlHelper(SafeDriveApplication.getContext());

    public ModelSql() {

    }

    public void add(RideDescription rideDescription) {
        SQLiteDatabase database = sqlHelper.getWritableDatabase();
        if (!SqlManager.update(database, rideDescription))
            SqlManager.add(database, rideDescription);
    }

    private boolean checkIfExist(RideDescription rideDescription) {
        SQLiteDatabase database = sqlHelper.getReadableDatabase();
        return SqlManager.checkIfExist(database, rideDescription.getFileName());
    }

    public List<RideDescription> getAllRides() {
        SQLiteDatabase database = sqlHelper.getReadableDatabase();
        return SqlManager.getAllRides(database);
    }

    public List<RideDescription> getAllDriverRides(String driverID) {
        SQLiteDatabase database = sqlHelper.getReadableDatabase();
        return SqlManager.getAllDriverRides(driverID, database);
    }


    class SqlHelper extends SQLiteOpenHelper {

        public SqlHelper(Context context) {
            super(context, SqlManager.DATABASE_NAME, null, SqlManager.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            SqlManager.create(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            SqlManager.drop(db);
            onCreate(db);
        }
    }
}
