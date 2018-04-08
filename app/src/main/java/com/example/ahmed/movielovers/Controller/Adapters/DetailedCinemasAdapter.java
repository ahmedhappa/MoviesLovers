package com.example.ahmed.movielovers.Controller.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.ahmed.movielovers.Controller.Interfaces.OnClickCinemaBook;
import com.example.ahmed.movielovers.Model.Cinema;
import com.example.ahmed.movielovers.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailedCinemasAdapter extends RecyclerView.Adapter<DetailedCinemasAdapter.ViewHolder> {
    private List<Cinema> cinemas;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnClickCinemaBook onClickCinemaBook;

    public DetailedCinemasAdapter(Context context, List<Cinema> cinemas) {
        this.cinemas = cinemas;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setOnClickCinemaBook(OnClickCinemaBook onClickCinemaBook) {
        this.onClickCinemaBook = onClickCinemaBook;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.recycler_items_detailed_cinemas, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context)
                .load(cinemas.get(position).getCinemaImage())
                .into(holder.cinemaImg);
    }

    @Override
    public int getItemCount() {
        return cinemas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cinemaImg;
        Button book;

        ViewHolder(View itemView) {
            super(itemView);
            cinemaImg = itemView.findViewById(R.id.detailed_cinema_image);
            book = itemView.findViewById(R.id.detailed_book_button);
            book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickCinemaBook.onCLick(getAdapterPosition());
                }
            });
        }
    }
}
