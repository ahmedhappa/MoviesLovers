package com.example.ahmed.movielovers.Controller;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;

import com.example.ahmed.movielovers.Controller.Activities.Reservation;
import com.example.ahmed.movielovers.Model.Cinema;
import com.example.ahmed.movielovers.Model.ContractClass;
import com.example.ahmed.movielovers.Model.Movie;
import com.example.ahmed.movielovers.R;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        Movie movie = null;
        Cinema cinema = null;
        ContentResolver contentResolver = context.getContentResolver();
        Uri moviesUri = ContractClass.MoviesTable.MOVIES_TABLE_PATH;
        Cursor movieCursor = contentResolver.query(moviesUri, null, null, null, null);
        if (movieCursor != null && movieCursor.moveToNext()) {
            movie = new Movie();
            movie.setName(movieCursor.getString(movieCursor.getColumnIndex(ContractClass.MoviesTable.MOVIE_NAME)));
            movie.setImg(movieCursor.getString(movieCursor.getColumnIndex(ContractClass.MoviesTable.MOVIE_IMAGE)));
            movie.setShortDescription(movieCursor.getString(movieCursor.getColumnIndex(ContractClass.MoviesTable.SHORT_DESCRIPTION)));
            movie.setRate(movieCursor.getString(movieCursor.getColumnIndex(ContractClass.MoviesTable.MOVIE_RATE)));
            movie.setDate(movieCursor.getString(movieCursor.getColumnIndex(ContractClass.MoviesTable.MOVIE_DATE)));
            movie.setDescription(movieCursor.getString(movieCursor.getColumnIndex(ContractClass.MoviesTable.DESCRIPTION)));
            movieCursor.close();
        }

        Uri cinemaUri = ContractClass.CinemaTable.CINEMAS_TABLE_PATH;
        Cursor cinemaCursor = contentResolver.query(cinemaUri, null, null, null, null);
        if (cinemaCursor != null && cinemaCursor.moveToNext()) {
            cinema = new Cinema();
            cinema.setName(cinemaCursor.getString(cinemaCursor.getColumnIndex(ContractClass.CinemaTable.CINEMA_NAME)));
            cinema.setCinemaImage(cinemaCursor.getString(cinemaCursor.getColumnIndex(ContractClass.CinemaTable.CINEMA_IMAGE)));
            cinema.setPrice(cinemaCursor.getString(cinemaCursor.getColumnIndex(ContractClass.CinemaTable.PRICE)));
            cinema.setPolicy(cinemaCursor.getString(cinemaCursor.getColumnIndex(ContractClass.CinemaTable.POLICY)));
            cinema.setLocation(cinemaCursor.getString(cinemaCursor.getColumnIndex(ContractClass.CinemaTable.LOCATION)));
            cinema.setLat(cinemaCursor.getDouble(cinemaCursor.getColumnIndex(ContractClass.CinemaTable.LAT)));
            cinema.setLng(cinemaCursor.getDouble(cinemaCursor.getColumnIndex(ContractClass.CinemaTable.LANG)));
            cinemaCursor.close();
        }
        if (movie != null) {
            views.setTextViewText(R.id.widget_movie_name, movie.getName());
            views.setTextViewText(R.id.widget_movie_date, movie.getDate());
            views.setTextViewText(R.id.widget_cinema_name, cinema.getName());
            views.setTextViewText(R.id.widget_cinema_location, cinema.getLocation());
            Intent intent = new Intent(context, Reservation.class);
            intent.putExtra("movie_data", movie);
            intent.putExtra("cinema_data", cinema);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.widget_root_view, pendingIntent);
        } else {
            views.setTextViewText(R.id.widget_movie_name, "Haven't initialized yet");
            views.setTextViewText(R.id.widget_movie_date, "Haven't initialized yet");
            views.setTextViewText(R.id.widget_cinema_name, "Haven't initialized yet");
            views.setTextViewText(R.id.widget_cinema_location, "Haven't initialized yet");
        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

