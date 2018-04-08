package com.example.ahmed.movielovers.Controller.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.ahmed.movielovers.Controller.Fragments.MovieCinemas;
import com.example.ahmed.movielovers.Controller.Fragments.MovieDetails;
import com.example.ahmed.movielovers.Model.Movie;
import com.example.ahmed.movielovers.R;

public class DetailedActivity extends AppCompatActivity {
    TabLayout detailedTabLayout;
    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        Intent movieData = getIntent();
        if (movieData.hasExtra("movie_data")) {
            Movie movie = movieData.getParcelableExtra("movie_data");
            setTitle(movie.getName());
        }

        detailedTabLayout = findViewById(R.id.detailed_tab_layout);
        currentFragment = new MovieDetails();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.detailed_fragment, currentFragment);
        fragmentTransaction.commit();
        detailedTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getText().toString().equals(getString(R.string.movie_detail))) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    currentFragment = new MovieDetails();
                    fragmentTransaction.replace(R.id.detailed_fragment, currentFragment);
                    fragmentTransaction.commit();
                } else {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    currentFragment = new MovieCinemas();
                    fragmentTransaction.replace(R.id.detailed_fragment, currentFragment);
                    fragmentTransaction.commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
