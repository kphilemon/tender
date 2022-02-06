package com.example.tender.utils.adapter.recyclerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class DiscoverFriendsAdapter extends RecyclerView.Adapter<DiscoverFriendsAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<UserWrapper> users;
    private final RecyclerViewInterface recyclerViewInterface;

    public DiscoverFriendsAdapter(Context context, ArrayList<UserWrapper> users, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.users = users;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_add_friends, parent, false);
        return new ViewHolder(view, recyclerViewInterface);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final UserWrapper userWrapper = users.get(position);
        final User user = userWrapper.getUser();
        holder.title.setText(user.getDisplayName());
        holder.subtitle.setText(user.getUsername());
        Glide.with(context).load(user.getPhotoUrl()).into(holder.image);
        holder.addFriendIcon.setVisibility(userWrapper.isFriend() ? View.INVISIBLE : View.VISIBLE);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView subtitle;
        private final ShapeableImageView image;
        private final ImageView addFriendIcon;

        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            title = itemView.findViewById(R.id.title_text);
            subtitle = itemView.findViewById(R.id.subtitle_text);
            image = itemView.findViewById(R.id.image);
            addFriendIcon = itemView.findViewById(R.id.add_friend_icon);

            itemView.setOnClickListener(v -> {
                if (recyclerViewInterface != null) {
                    // or use the getAdapterPosition()
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(pos);
                    }
                }
            });
        }
    }
}
