package com.example.movies.detail_screen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.R;
import com.example.movies.net.entities.Trailer;

import java.util.ArrayList;
import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder> {
    private TrailersOnClickListener trailersOnClickListener;

    public void setTrailersOnClickListener(TrailersOnClickListener trailersOnClickListener) {
        this.trailersOnClickListener = trailersOnClickListener;
    }

    private List<Trailer> list = new ArrayList<>();

    public void setList(List<Trailer> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_item,
                        parent,
                        false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        Trailer trailer = list.get(position);
        holder.textViewTrailer.setText(trailer.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trailersOnClickListener != null) {
                    trailersOnClickListener.trailerOnClick(trailer);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface TrailersOnClickListener {
        void trailerOnClick(Trailer trailer);
    }

    static class TrailerViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTrailer;

        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTrailer = itemView.findViewById(R.id.textViewTrailer);
        }
    }
}
