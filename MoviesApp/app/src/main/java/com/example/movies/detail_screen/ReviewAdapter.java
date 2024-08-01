package com.example.movies.detail_screen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.R;
import com.example.movies.net.entities.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<Review> reviewsLists = new ArrayList<>();
    private OnReviewClick onReviewClick;

    public void setOnReviewClickListener(OnReviewClick onReviewClickListener) {
        this.onReviewClick = onReviewClickListener;
    }

    public void setReviewsLists(List<Review> reviewsLists) {
        this.reviewsLists = reviewsLists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item,
                        parent,
                        false
                );
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviewsLists.get(position);
        holder.textViewAuthor.setText(review.getAuthorName());
        if (review.getTitle() == "") {
            holder.textViewTitle.setText("Без названия");
        } else {
            holder.textViewTitle.setText(review.getTitle());
        }

        holder.textViewReview.setText(review.getReview());

        holder.reviewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onReviewClick != null) {
                    onReviewClick.onReviewClick(review);
                }
            }
        });

        holder.textViewLike.setText(String.valueOf(review.getReviewLikes()));
        holder.textViewDislike.setText(String.valueOf(review.getReviewDislikes()));
        int background;
        switch (review.getType()) {
            case "Позитивный":
                background = R.color.green_tea;
                break;
            case "Негативный":
                background = R.color.losos;
                break;
            case "Нейтральный":
                background = R.color.light_gray;
                break;
            default:
                background = R.color.white;
        }
        int backgroundColor = ContextCompat.getColor(holder.itemView.getContext(), background);
        holder.reviewHeader.setBackgroundColor(backgroundColor);

    }

    public interface OnReviewClick {
        void onReviewClick(Review review);
    }

    @Override
    public int getItemCount() {
        return reviewsLists.size();
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewAuthor;
        private TextView textViewReview;
        private TextView textViewLike;
        private TextView textViewDislike;
        private LinearLayout reviewHeader;
        private CardView reviewItem;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewItem = itemView.findViewById(R.id.cardViewReview);
            reviewHeader = itemView.findViewById(R.id.review_header);
            textViewTitle = itemView.findViewById(R.id.textViewReviewTitle);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textViewReview = itemView.findViewById(R.id.textViewReviewBody);
            textViewLike = itemView.findViewById(R.id.textViewLike);
            textViewDislike = itemView.findViewById(R.id.textViewDislike);
        }
    }
}
