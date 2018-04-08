package com.example.ahmed.movielovers.Model;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MyContentProvider extends ContentProvider {
    private Database database;
    private static final UriMatcher uriMatcher = buildUriMatcher();

    @Override
    public boolean onCreate() {
        Context context = getContext();
        database = new Database(context);
        return true;
    }

    public static final int movies = 100;
    public static final int cinemas = 200;

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(ContractClass.AUTHORITY, ContractClass.MoviesTable.MOVIES_PATH, movies);
        uriMatcher.addURI(ContractClass.AUTHORITY, ContractClass.CinemaTable.CINEMAS_PATH, cinemas);
        return uriMatcher;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        SQLiteDatabase sqLiteDatabase = database.getReadableDatabase();
        Cursor cursor;
        int matcher = uriMatcher.match(uri);
        switch (matcher) {
            case movies:
                cursor = sqLiteDatabase.query(ContractClass.MoviesTable.TABLE_NAME
                        , strings
                        , s
                        , strings1
                        , null
                        , null
                        , s1);
                break;
            case cinemas:
                cursor = sqLiteDatabase.query(ContractClass.CinemaTable.TABLE_NAME
                        , strings
                        , s
                        , strings1
                        , null
                        , null
                        , s1);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        int matcher = uriMatcher.match(uri);
        Uri returnedUri;
        switch (matcher) {
            case movies:
                delete(uri, null, null);
                long movieInsertion = sqLiteDatabase.insert(ContractClass.MoviesTable.TABLE_NAME, null, contentValues);
                if (movieInsertion > 0) {
                    returnedUri = ContentUris.withAppendedId(ContractClass.MoviesTable.MOVIES_TABLE_PATH, movieInsertion);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            case cinemas:
                delete(uri, null, null);
                long cinemaInsertion = sqLiteDatabase.insert(ContractClass.CinemaTable.TABLE_NAME, null, contentValues);
                if (cinemaInsertion > 0) {
                    returnedUri = ContentUris.withAppendedId(ContractClass.CinemaTable.CINEMAS_TABLE_PATH, cinemaInsertion);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnedUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        int matcher = uriMatcher.match(uri);
        int deletedTasks = 0;
        switch (matcher) {
            case movies:
                deletedTasks = sqLiteDatabase.delete(ContractClass.MoviesTable.TABLE_NAME, null, null);
                break;
            case cinemas:
                deletedTasks = sqLiteDatabase.delete(ContractClass.CinemaTable.TABLE_NAME, null, null);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (deletedTasks != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deletedTasks;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
