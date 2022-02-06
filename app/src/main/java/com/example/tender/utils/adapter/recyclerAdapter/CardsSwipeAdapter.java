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
import com.example.tender.model.ActiveMatches;
import java.util.List;

public class CardsSwipeAdapter extends RecyclerView.Adapter<CardsSwipeAdapter.ViewHolder> {
    List<ActiveMatches> cardsList;
    Context context;
    public CardsSwipeAdapter(List<ActiveMatches> cardsList, Context context){
        this.cardsList = cardsList;
        this.context = context;
    }

    @NonNull
    @Override
    public CardsSwipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_swipe_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ActiveMatches match = cardsList.get(position);
        holder.foodName.setText(match.getMatchTitle());
        holder.foodSubtitle.setText(match.getMatchDesc());
        Glide.with(context).load(match.getMatchImage()).into(holder.foodImage);
    }


    @Override
    public int getItemCount() {
        return cardsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView foodImage;
        private final TextView foodName;
        private final TextView foodSubtitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.item_image);
            foodName = itemView.findViewById(R.id.item_label);
            foodSubtitle = itemView.findViewById(R.id.item_sub_label);

        }
    }
}
