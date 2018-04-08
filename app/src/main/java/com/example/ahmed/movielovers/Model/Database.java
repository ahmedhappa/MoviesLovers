package com.example.ahmed.movielovers.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {
    private final static String databaseName = "movieLovers.dp";
    private final static int version = 1;

    public Database(Context context) {
        super(context, databaseName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL("create table " + ContractClass.MoviesTable.TABLE_NAME + " ( "
                    + ContractClass.MoviesTable.MOVIE_NAME + " TEXT not null , "
                    + ContractClass.MoviesTable.SHORT_DESCRIPTION + " TEXT not null , "
                    + ContractClass.MoviesTable.MOVIE_IMAGE + " TEXT not null , "
                    + ContractClass.MoviesTable.MOVIE_RATE + " TEXT not null , "
                    + ContractClass.MoviesTable.MOVIE_DATE + " TEXT not null , "
                    + ContractClass.MoviesTable.DESCRIPTION + " TEXT not null );");

            sqLiteDatabase.execSQL("create table " + ContractClass.CinemaTable.TABLE_NAME + " ( "
                    + ContractClass.CinemaTable.CINEMA_NAME + " TEXT not null , "
                    + ContractClass.CinemaTable.LOCATION + " TEXT not null , "
                    + ContractClass.CinemaTable.CINEMA_IMAGE + " TEXT not null , "
                    + ContractClass.CinemaTable.PRICE + " TEXT not null , "
                    + ContractClass.CinemaTable.POLICY + " TEXT not null , "
                    + ContractClass.CinemaTable.LAT + " REAL not null , "
                    + ContractClass.CinemaTable.LANG + " REAL not null );");
            Log.i("Database Creation : ", "database created successfully");
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.i("Database Creation : ", "database creation failed");
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
