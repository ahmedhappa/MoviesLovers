package com.example.ahmed.movielovers.Controller.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahmed.movielovers.Controller.Interfaces.OnClickHomeMovieBook;
import com.example.ahmed.movielovers.Model.Movie;
import com.example.ahmed.movielovers.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private List<Movie> movies;
    private LayoutInflater layoutInflater;
    private Context context;
    private OnClickHomeMovieBook onClickHomeMovieBook;

    public HomeAdapter(List<Movie> movies, Context context) {
        this.movies = movies;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setOnClickHomeMovieBook(OnClickHomeMovieBook onClickHomeMovieBook) {
        this.onClickHomeMovieBook = onClickHomeMovieBook;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycler_items_home_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(movies.get(position).getName());
        holder.shortDesc.setText(movies.get(position).getShortDescription());
        Picasso.with(context)
                .load(movies.get(position).getImg())
                .fit()
                .into(holder.movieImg);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImg;
        TextView name, shortDesc;
        Button button;

        ViewHolder(View itemView) {
            super(itemView);
            movieImg = itemView.findViewById(R.id.home_movie_img);
            name = itemView.findViewById(R.id.home_movie_name);
            shortDesc = itemView.findViewById(R.id.home_movie_desc);
            button = itemView.findViewById(R.id.home_movie_btn);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickHomeMovieBook.onClick(movies.get(getAdapterPosition()));
                }
            });
        }
    }
}
