package com.example.ahmed.movielovers.Controller.Activities;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmed.movielovers.Controller.AppWidget;
import com.example.ahmed.movielovers.Model.Cinema;
import com.example.ahmed.movielovers.Model.ContractClass;
import com.example.ahmed.movielovers.Model.Movie;
import com.example.ahmed.movielovers.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Reservation extends AppCompatActivity {
    TextView movieName, cinemaName, movieDate, cinemaLocation, ticktPrice, numOfPeople, totalPrice, cinemaPolicy;
    Button incPeople, decPeople, bookATicket, cinemaMapLocation;
    Cinema cinema;
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        movieName = findViewById(R.id.reservation_movie_name);
        cinemaName = findViewById(R.id.reservation_cinema_name);
        movieDate = findViewById(R.id.reservation_movie_date);
        cinemaLocation = findViewById(R.id.reservation_cinema_location);
        ticktPrice = findViewById(R.id.reservation_ticket_price);
        numOfPeople = findViewById(R.id.reservation_number_of_people);
        totalPrice = findViewById(R.id.reservation_total_price);
        cinemaPolicy = findViewById(R.id.reservation_cinema_policy);

        incPeople = findViewById(R.id.reservation_up_btn);
        decPeople = findViewById(R.id.reservation_down_btn);
        bookATicket = findViewById(R.id.reservation_book_btn);
        cinemaMapLocation = findViewById(R.id.reservation_cinema_location_btn);

        Intent movieData = getIntent();
        if (movieData.hasExtra("movie_data")) {
            movie = movieData.getParcelableExtra("movie_data");
            int cinemaNumber = movieData.getIntExtra("cinema_number", -1);
            if (cinemaNumber != -1) {
                cinema = movie.getCinemas().get(cinemaNumber);
            } else {
                cinema = movieData.getParcelableExtra("cinema_data");
            }
            movieName.setText(movie.getName());
            movieDate.setText(movie.getDate());
            cinemaName.setText(cinema.getName());
            cinemaLocation.setText(cinema.getLocation());
            ticktPrice.setText(cinema.getPrice() + "$");
            cinemaPolicy.setText(cinema.getPolicy());
            numOfPeople.setText("1");
            totalPrice.setText(cinema.getPrice() + "$");
        }

        incPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numPeople = Integer.parseInt(numOfPeople.getText().toString());
                int tPrice = Integer.parseInt(ticktPrice.getText().toString().substring(0, ticktPrice.getText().toString().length() - 1));
                numPeople++;
                tPrice = numPeople * tPrice;
                numOfPeople.setText(numPeople + "");
                totalPrice.setText(tPrice + "$");
            }
        });

        decPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numPeople = Integer.parseInt(numOfPeople.getText().toString());
                int tPrice = Integer.parseInt(ticktPrice.getText().toString().substring(0, ticktPrice.getText().toString().length() - 1));
                if (numPeople > 1) {
                    numPeople--;
                    tPrice = numPeople * tPrice;
                    numOfPeople.setText(numPeople + "");
                    totalPrice.setText(tPrice + "$");
                }
            }
        });

        cinemaMapLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cinema != null) {
                    Intent intent = new Intent(Reservation.this, MapsActivity.class);
                    intent.putExtra("lat", cinema.getLat());
                    intent.putExtra("lng", cinema.getLng());
                    startActivity(intent);
                } else {
                    Toast.makeText(Reservation.this, getString(R.string.try_again), Toast.LENGTH_SHORT).show();
                }

            }
        });

        bookATicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference();
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Reservation.this);
                    databaseReference.child("reservations").child(sharedPreferences.getString("user_account", "").substring(0, sharedPreferences.getString("user_account", "").indexOf("@"))).child("Movie Name").setValue(movieName.getText().toString());
                    databaseReference.child("reservations").child(sharedPreferences.getString("user_account", "").substring(0, sharedPreferences.getString("user_account", "").indexOf("@"))).child("Cinema Name").setValue(cinemaName.getText().toString());
                    databaseReference.child("reservations").child(sharedPreferences.getString("user_account", "").substring(0, sharedPreferences.getString("user_account", "").indexOf("@"))).child("Number of people").setValue(numOfPeople.getText().toString());
                    databaseReference.child("reservations").child(sharedPreferences.getString("user_account", "").substring(0, sharedPreferences.getString("user_account", "").indexOf("@"))).child("Total Price").setValue(totalPrice.getText().toString());
                    try {
                        new myAsynctask().execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(Reservation.this, getString(R.string.operation_done), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Reservation.this, getString(R.string.operation_fail), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    class myAsynctask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            ContentResolver contentResolver = getContentResolver();
            Uri moviesUri = ContractClass.MoviesTable.MOVIES_TABLE_PATH;
            ContentValues movieValues = new ContentValues();
            movieValues.put(ContractClass.MoviesTable.MOVIE_NAME, movie.getName());
            movieValues.put(ContractClass.MoviesTable.SHORT_DESCRIPTION, movie.getShortDescription());
            movieValues.put(ContractClass.MoviesTable.MOVIE_IMAGE, movie.getImg());
            movieValues.put(ContractClass.MoviesTable.MOVIE_RATE, movie.getRate());
            movieValues.put(ContractClass.MoviesTable.MOVIE_DATE, movie.getDate());
            movieValues.put(ContractClass.MoviesTable.DESCRIPTION, movie.getDescription());
            contentResolver.insert(moviesUri, movieValues);

            Uri cinemaUri = ContractClass.CinemaTable.CINEMAS_TABLE_PATH;
            ContentValues cinemaValues = new ContentValues();
            cinemaValues.put(ContractClass.CinemaTable.CINEMA_NAME, cinema.getName());
            cinemaValues.put(ContractClass.CinemaTable.LOCATION, cinema.getLocation());
            cinemaValues.put(ContractClass.CinemaTable.CINEMA_IMAGE, cinema.getCinemaImage());
            cinemaValues.put(ContractClass.CinemaTable.PRICE, cinema.getPrice());
            cinemaValues.put(ContractClass.CinemaTable.POLICY, cinema.getPolicy());
            cinemaValues.put(ContractClass.CinemaTable.LAT, cinema.getLat());
            cinemaValues.put(ContractClass.CinemaTable.LANG, cinema.getLng());
            Uri result = contentResolver.insert(cinemaUri, cinemaValues);
            if (result != null && !result.toString().equals("")) {
                //update widget after reservation
                Intent widgetIntent = new Intent(Reservation.this, AppWidget.class);
                widgetIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                int[] widgetIds = AppWidgetManager.getInstance(Reservation.this).
                        getAppWidgetIds(new ComponentName(Reservation.this, AppWidget.class));
                widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetIds);
                Reservation.this.sendBroadcast(widgetIntent);
                Log.i("Data insertion", "Data inserted Successfully");
            } else {
                Log.i("Data insertion", "Data insertion failed");
            }
            return null;
        }
    }
}
