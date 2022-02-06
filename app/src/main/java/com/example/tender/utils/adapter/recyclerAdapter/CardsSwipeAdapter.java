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
import com.example.tender.model.Food;
import com.example.tender.model.FoodWrapper;

import java.util.ArrayList;

public class CardsSwipeAdapter extends RecyclerView.Adapter<CardsSwipeAdapter.ViewHolder> {
    Context context;
    ArrayList<FoodWrapper> foods;

    public CardsSwipeAdapter(Context context, ArrayList<FoodWrapper> foods) {
        this.context = context;
        this.foods = foods;
    }

    @NonNull
    @Override
    public CardsSwipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_swipe_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final FoodWrapper foodWrapper = foods.get(position);
        final Food food = foodWrapper.getFood();
        holder.title.setText(food.getName());
        holder.subtitle.setText(food.getDescription());
        Glide.with(context).load(food.getPhotoUrl()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView title;
        private final TextView subtitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            subtitle = itemView.findViewById(R.id.subtitle);
        }
    }
}
