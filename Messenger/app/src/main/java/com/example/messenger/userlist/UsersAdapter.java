package com.example.messenger.userlist;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messenger.R;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersAdapterViewHolder> {
    private List<User> userList = new ArrayList<>();
    private OnUserClickListener onUserClickListener;

    public void setOnUserClickListener(OnUserClickListener onUserClickListener) {
        this.onUserClickListener = onUserClickListener;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UsersAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item,
                        parent, false);
        return new UsersAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapterViewHolder holder, int position) {
        User user = userList.get(position);
        String online = "online";
        String offline = "offline";
        String userInfo = String.format("%s %s, %s", user.getName(), user.getLastName(), user.getAge());
        holder.textViewUserInfo.setText(userInfo);
        int bgResId;
        if (user.isOnline()) {
            bgResId = R.drawable.circle_online;
            holder.textViewStatus.setText(online);
        } else {
            bgResId = R.drawable.circle_offline;
            holder.textViewStatus.setText(offline);
        }
        Drawable background = ContextCompat.getDrawable(holder.itemView.getContext(), bgResId);
        holder.viewStatus.setBackground(background);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onUserClickListener != null) {
                    onUserClickListener.OnUserClick(user);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    interface OnUserClickListener {
        void OnUserClick(User user);
    }

    static class UsersAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewUserInfo;
        private TextView textViewStatus;
        private View viewStatus;

        public UsersAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUserInfo = itemView.findViewById(R.id.textViewUserInfo);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            viewStatus = itemView.findViewById(R.id.viewStatus);
        }
    }
}
