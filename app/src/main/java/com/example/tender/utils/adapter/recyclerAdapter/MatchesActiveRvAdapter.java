package com.example.tender.utils.adapter.recyclerAdapter;

import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tender.R;
import com.example.tender.model.ActiveMatches;
import com.example.tender.model.User;

import java.util.ArrayList;

public class MatchesActiveRvAdapter extends RecyclerView.Adapter<MatchesActiveRvAdapter.MyViewHolder> {

    private final ArrayList<ActiveMatches> activeMatches;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView matchImage;
        private final TextView matchTitle;
        private final TextView matchDesc;
        private final TextView matchTime;
        private final CardView matchStatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            matchImage = itemView.findViewById(R.id.active_matches_image);
            matchTitle = itemView.findViewById(R.id.active_matches_title_txt);
            matchDesc = itemView.findViewById(R.id.active_matches_description_txt);
            matchTime = itemView.findViewById(R.id.active_matches_time_tv);
            matchStatus = itemView.findViewById(R.id.active_status_cardView);
        }
    }

    @NonNull
    @Override
    public MatchesActiveRvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_active_match, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesActiveRvAdapter.MyViewHolder holder, int position) {

        final ActiveMatches matches = activeMatches.get(position);

        /**
         * Load image using Glide here
         */
        Glide.with(holder.itemView.getContext()).load(R.drawable.bg_tender).into(holder.matchImage);
        holder.matchTitle.setText(matches.getMatchTitle());
        holder.matchDesc.setText(matches.getMatchDesc());
        holder.matchTime.setText(matches.getMatchTime());

        /**
         * Should we do the switch here?
         * 0 : Ongoing
         * 1 : Pending
         */
        switch (matches.getMatchStatus()) {
            case 0:
                holder.matchStatus.setCardBackgroundColor(Color.parseColor("#46D160"));
                break;
            case 1:
                holder.matchStatus.setCardBackgroundColor(Color.parseColor("#FFBA25"));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return activeMatches.size();
    }

    public MatchesActiveRvAdapter(ArrayList<ActiveMatches> activeMatches) {
        this.activeMatches = activeMatches;
    }


}


