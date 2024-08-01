package com.example.movies.home_screen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.movies.MoviesAdapter;
import com.example.movies.R;
import com.example.movies.net.entities.Movie;

import java.util.ArrayList;
import java.util.List;

public class TopMoviesAdapter extends RecyclerView.Adapter<TopMoviesAdapter.TopMoviesViewHolder> {

    private List<Movie> movies = new ArrayList<>();
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public List<Movie> getMovies() {
        return movies;
    }
    private MoviesAdapter.OnMovieClickListener onMovieClickListener;

    public void setOnMovieClickListener(MoviesAdapter.OnMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }

    @NonNull
    @Override
    public TopMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                        parent.getContext())
                .inflate(
                        R.layout.top_ten_item
                        , parent
                        , false
                );
        return new TopMoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopMoviesViewHolder holder, int position) {
        Movie movie = movies.get(position);
        if (movie.getPoster() == null) {
            Glide.with(holder.itemView)
                    .load("https://st.kp.yandex.net/images/no-poster.gif")
                    .into(holder.imageViewPosterTop);
        } else {
            Glide.with(holder.itemView)
                    .load(movie.getPoster().getUrl())
                    .apply(new RequestOptions().override(2400, 800))
                    .into(holder.imageViewPosterTop);
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

    static class TopMoviesViewHolder extends RecyclerView.ViewHolder{
        private final ImageView imageViewPosterTop;
        public TopMoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPosterTop = itemView.findViewById(R.id.imageViewPosterTop);
        }
    }
}
