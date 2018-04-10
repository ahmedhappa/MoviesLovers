package com.example.ahmed.movielovers.Controller.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ahmed.movielovers.Controller.Activities.Reservation;
import com.example.ahmed.movielovers.Controller.Adapters.DetailedCinemasAdapter;
import com.example.ahmed.movielovers.Controller.Interfaces.OnClickCinemaBook;
import com.example.ahmed.movielovers.Model.Cinema;
import com.example.ahmed.movielovers.Model.Movie;
import com.example.ahmed.movielovers.R;

import java.util.List;

public class MovieCinemas extends Fragment implements OnClickCinemaBook {
    RecyclerView detailedRecyclerCinema;
    List<Cinema> cinemas;
    Movie movie;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_cinemas, container, false);
        detailedRecyclerCinema = view.findViewById(R.id.detailed_recycler_cinemas);
        Intent movieData = getActivity().getIntent();

        if (movieData.hasExtra("movie_data")) {
            movie = movieData.getParcelableExtra("movie_data");
            cinemas = movie.getCinemas();
            DetailedCinemasAdapter detailedCinemasAdapter = new DetailedCinemasAdapter(getActivity(), cinemas);
            detailedRecyclerCinema.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            detailedRecyclerCinema.setAdapter(detailedCinemasAdapter);
            detailedCinemasAdapter.setOnClickCinemaBook(this);
        } else {
            Toast.makeText(getActivity(), getString(R.string.try_again), Toast.LENGTH_SHORT).show();
        }

        return view;
    }


    @Override
    public void onCLick(int cinemaNumber) {
        Intent intent = new Intent(getActivity(), Reservation.class);
        intent.putExtra("movie_data", movie);
        intent.putExtra("cinema_number", cinemaNumber);
        startActivity(intent);
    }
}
