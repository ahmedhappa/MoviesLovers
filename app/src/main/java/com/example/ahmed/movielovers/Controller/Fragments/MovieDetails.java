package com.example.ahmed.movielovers.Controller.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmed.movielovers.Model.Movie;
import com.example.ahmed.movielovers.R;
import com.squareup.picasso.Picasso;

public class MovieDetails extends Fragment {
    ImageView movieImage;
    TextView rate, date, description;
    Movie movie;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detailes, container, false);
        movieImage = view.findViewById(R.id.detailed_movie_image);
        rate = view.findViewById(R.id.detailed_movie_rate);
        date = view.findViewById(R.id.detailed_movie_date);
        description = view.findViewById(R.id.detailed_movie_description);


        Intent movieData = getActivity().getIntent();
        if (movieData.hasExtra("movie_data")) {
            movie = movieData.getParcelableExtra("movie_data");
            Picasso.with(getActivity())
                    .load(movie.getImg())
                    .fit()
                    .into(movieImage);
            rate.setText(movie.getRate());
            date.setText(movie.getDate());
            description.setText(movie.getDescription());
        } else {
            Toast.makeText(getActivity(), getString(R.string.try_again), Toast.LENGTH_SHORT).show();
        }

        return view;
    }
}
