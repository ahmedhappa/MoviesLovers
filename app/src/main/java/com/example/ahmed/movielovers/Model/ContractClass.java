package com.example.ahmed.movielovers.Model;

import android.net.Uri;
import android.provider.BaseColumns;

public class ContractClass {

    private ContractClass() {
    }

    public static final String AUTHORITY = "com.example.ahmed.movielovers.Model.MyContentProvider";
    private static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

    public static final class MoviesTable implements BaseColumns {
        public static final String MOVIES_PATH = "movies";
        public static final Uri MOVIES_TABLE_PATH = BASE_URI.buildUpon().appendPath(MOVIES_PATH).build();

        public static final String TABLE_NAME = "movies";
        public static final String MOVIE_NAME = "name";
        public static final String SHORT_DESCRIPTION = "short_description";
        public static final String MOVIE_IMAGE = "image";
        public static final String MOVIE_RATE = "rate";
        public static final String MOVIE_DATE = "date";
        public static final String DESCRIPTION = "description";
    }

    public static final class CinemaTable implements BaseColumns {
        public static final String CINEMAS_PATH = "cinemas";
        public static final Uri CINEMAS_TABLE_PATH = BASE_URI.buildUpon().appendPath(CINEMAS_PATH).build();

        public static final String TABLE_NAME = "cinemas";
        public static final String CINEMA_NAME = "name";
        public static final String LOCATION = "location";
        public static final String CINEMA_IMAGE = "image";
        public static final String PRICE = "price";
        public static final String POLICY = "policy";
        public static final String LAT = "lat";
        public static final String LANG = "lang";
    }
}
