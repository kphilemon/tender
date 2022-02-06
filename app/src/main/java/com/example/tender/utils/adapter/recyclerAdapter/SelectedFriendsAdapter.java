package com.example.tender.utils.adapter.recyclerAdapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tender.R;
import com.example.tender.model.User;
import com.example.tender.model.UserWrapper;
import com.example.tender.ui.RecyclerViewInterface;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class SelectedFriendsAdapter extends RecyclerView.Adapter<SelectedFriendsAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<UserWrapper> friends;
    private final RecyclerViewInterface recyclerViewInterface;

    public SelectedFriendsAdapter(Context context, ArrayList<UserWrapper> friends, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.friends = friends;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_selected_friends, parent, false);
        return new ViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final User user = friends.get(position).getUser();
        holder.title.setText(user.getDisplayName());
        Glide.with(context).load(user.getPhotoUrl()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final ShapeableImageView image;

        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            title = itemView.findViewById(R.id.title_text);
            image = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(v -> {
                if (recyclerViewInterface != null) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(pos);
                    }
                }
            });
        }
    }
}
