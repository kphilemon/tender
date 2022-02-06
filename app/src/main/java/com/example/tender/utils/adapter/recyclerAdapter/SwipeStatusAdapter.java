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
import com.example.tender.model.SwipeStatus;

import java.util.ArrayList;

public class SwipeStatusAdapter extends RecyclerView.Adapter<SwipeStatusAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<SwipeStatus> swipeStatuses;

    public SwipeStatusAdapter(Context context, ArrayList<SwipeStatus> swipeStatuses) {
        this.context = context;
        this.swipeStatuses = swipeStatuses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_row_general, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SwipeStatus status = swipeStatuses.get(position);
        holder.title.setText(status.getDisplayName());
        holder.subtitle.setText(status.getStatus() ? R.string.completed_swiping : R.string.pending);
        Glide.with(context).load(status.getPhotoUrl()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return swipeStatuses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView subtitle;
        private final ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_text);
            subtitle = itemView.findViewById(R.id.subtitle_text);
            image = itemView.findViewById(R.id.image);
        }
    }
}
