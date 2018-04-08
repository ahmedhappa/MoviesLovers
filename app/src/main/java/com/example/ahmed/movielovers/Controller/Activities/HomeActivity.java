package com.example.ahmed.movielovers.Controller.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ahmed.movielovers.Controller.Adapters.HomeAdapter;
import com.example.ahmed.movielovers.Controller.Interfaces.OnClickHomeMovieBook;
import com.example.ahmed.movielovers.Model.Cinema;
import com.example.ahmed.movielovers.Model.Movie;
import com.example.ahmed.movielovers.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements OnClickHomeMovieBook {
    RecyclerView recyclerView;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.home_recycler);
        final List<Movie> movies = new ArrayList<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<HashMap<String, Object>> moviesData = (List<HashMap<String, Object>>) dataSnapshot.child("movies").getValue();
                movies.clear();
                for (int i = 0; i < moviesData.size(); i++) {
                    Movie movie = new Movie();
                    movie.setName(moviesData.get(i).get("name").toString());
                    movie.setShortDescription(moviesData.get(i).get("short_description").toString());
                    movie.setImg(moviesData.get(i).get("image").toString());
                    movie.setRate(moviesData.get(i).get("rate").toString());
                    movie.setDate(moviesData.get(i).get("date").toString());
                    movie.setDescription(moviesData.get(i).get("description").toString());
                    List<Cinema> cinemas = new ArrayList<>();
                    List<HashMap<String, Object>> cinemasData = (List<HashMap<String, Object>>) moviesData.get(i).get("cinemas");
                    for (int j = 0; j < cinemasData.size(); j++) {
                        Cinema cinema = new Cinema();
                        cinema.setName(cinemasData.get(j).get("name").toString());
                        cinema.setCinemaImage(cinemasData.get(j).get("image").toString());
                        cinema.setLocation(cinemasData.get(j).get("location").toString());
                        cinema.setPolicy(cinemasData.get(j).get("policy").toString());
                        cinema.setPrice(cinemasData.get(j).get("price").toString());
                        cinema.setLat((Double) cinemasData.get(j).get("lat"));
                        cinema.setLng((Double) cinemasData.get(j).get("lng"));
                        cinemas.add(cinema);
                    }
                    movie.setCinemas(cinemas);
                    movies.add(movie);
                }

                HomeAdapter homeAdapter = new HomeAdapter(movies, context);
                homeAdapter.setOnClickHomeMovieBook(HomeActivity.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(homeAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(this, DetailedActivity.class);
        intent.putExtra("movie_data", movie);
        startActivity(intent);
    }
}
