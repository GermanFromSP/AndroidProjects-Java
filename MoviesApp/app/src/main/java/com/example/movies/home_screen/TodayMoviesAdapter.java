package com.example.movies.home_screen;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movies.R;
import com.example.movies.net.entities.Movie;

import java.util.ArrayList;
import java.util.List;

public class TodayMoviesAdapter extends RecyclerView.Adapter<TodayMoviesAdapter.TodayMoviesViewHolder> {
   private List<Movie> movies = new ArrayList<>();
   private OnMovieClickListener onMovieClickListener;

    public void setOnMovieClickListener(OnMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TodayMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                        parent.getContext())
                .inflate(
                        R.layout.today_movie_item
                        , parent
                        , false
                );
        return new TodayMoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodayMoviesViewHolder holder, int position) {
        Movie movie = movies.get(position);
        if (movie.getPoster() == null) {
            Glide.with(holder.itemView)
                    .load("https://st.kp.yandex.net/images/no-poster.gif")
                    .into(holder.posterToday);
        } else {
            Glide.with(holder.itemView)
                    .load(movie.getPoster().getUrl())
                    .override(2400, 800)
                    .into(holder.posterToday);
        }
        double rating = movie.getRating().getKp();
        int backgroundId;
        if (rating > 7) {
            backgroundId = R.drawable.circle_background_green;
        } else if (rating > 5) {
            backgroundId = R.drawable.circle_background_orange;
        } else {
            backgroundId = R.drawable.circle_background_red;
        }
        Drawable background = ContextCompat.getDrawable(holder.itemView.getContext(), backgroundId);
        holder.textViewTodayRating.setBackground(background);
        holder.textViewTodayRating.setText(String.format("%.1f", rating));
        holder.textViewTodayTitle.setText(String.format("\"%s\"", movie.getName()));
        holder.textViewTodayYear.setText(String.valueOf(movie.getYear()));
        if (movie.getDescription().length()<= 200) {
            holder.textViewTodayDescription.setText(String.format("%s...", movie.getDescription()));
        }else {
            holder.textViewTodayDescription.setText(String.format("%s...", (movie.getDescription()).substring(0, 200)));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMovieClickListener != null){
                    onMovieClickListener.onMovieClick(movie);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
    interface OnMovieClickListener{
        void onMovieClick(Movie movie);
    }

    static class TodayMoviesViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewTodayTitle;
        private TextView textViewTodayYear;
        private TextView textViewTodayRating;
        private TextView textViewTodayDescription;
        private ImageView posterToday;

        public TodayMoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTodayTitle = itemView.findViewById(R.id.textViewTodayTitle);
            textViewTodayYear = itemView.findViewById(R.id.textViewTodayYear);
            textViewTodayRating = itemView.findViewById(R.id.textViewTodayRating);
            textViewTodayDescription = itemView.findViewById(R.id.textViewTodayDescription);
            posterToday = itemView.findViewById(R.id.posterToday);

        }
    }
}
