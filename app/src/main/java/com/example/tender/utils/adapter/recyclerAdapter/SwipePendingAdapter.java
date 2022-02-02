package com.example.tender.utils.adapter.recyclerAdapter;

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
import com.example.tender.model.User;
import com.example.tender.ui.RecyclerViewInterface;

import java.util.ArrayList;

public class SwipePendingAdapter extends RecyclerView.Adapter<SwipePendingAdapter.MyViewHolder> {

    private final ArrayList<SwipeStatus> pendingList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView mainTv;
        private final TextView subtitleTv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            mainTv = itemView.findViewById(R.id.main_text_tv);
            subtitleTv = itemView.findViewById(R.id.subtitle_text_tv);
        }
    }

    @NonNull
    @Override
    public SwipePendingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_row_general, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SwipePendingAdapter.MyViewHolder holder, int position) {

        final SwipeStatus pendingStatus = pendingList.get(position);
        holder.mainTv.setText(pendingStatus.getName());
        Glide.with(holder.itemView.getContext()).load(R.drawable.avengers).into(holder.imageView);

        if (pendingStatus.getStatus()){
            holder.subtitleTv.setText(R.string.completed_swiping);
        } else {
            holder.subtitleTv.setText(R.string.pending);
        }

    }

    @Override
    public int getItemCount() {
        return pendingList.size();
    }

    public SwipePendingAdapter(ArrayList<SwipeStatus> pendingList) {
        this.pendingList = pendingList;
    }


}
