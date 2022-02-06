package com.example.tender.utils.adapter.recyclerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tender.R;
import com.example.tender.model.Match;
import com.example.tender.model.MatchWrapper;
import com.example.tender.ui.RecyclerViewInterface;
import com.example.tender.utils.appUtils.AppUtils;

import java.util.ArrayList;

public class MatchesListAdapter extends RecyclerView.Adapter<MatchesListAdapter.ViewHolder> {
    private final Context context;
    private final String userId;
    private final ArrayList<MatchWrapper> activeMatches;
    private final RecyclerViewInterface recyclerViewInterface;

    public MatchesListAdapter(Context context, String userId, ArrayList<MatchWrapper> activeMatches, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.userId = userId;
        this.activeMatches = activeMatches;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_match, parent, false);
        return new ViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Match match = activeMatches.get(position).getMatch();
        holder.title.setText(match.getName() + " with " + AppUtils.formatNames(match.getDisplayNames(), userId));
        holder.time.setText(match.getTimestamp().toDate().getDate() + "/" + match.getTimestamp().toDate().getMonth() + "/" + match.getTimestamp().toDate().toString().substring(match.getTimestamp().toDate().toString().length() - 4));

        boolean isAllSwipingCompleted = match.getSwipes().size() == match.getUserIds().size();
        holder.subtitle.setText(isAllSwipingCompleted ? "Matched!" : "Awaiting match");
        holder.status.setVisibility(isAllSwipingCompleted ? View.GONE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return activeMatches.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView matchImage;
        private final TextView title;
        private final TextView subtitle;
        private final TextView time;
        private final CardView status;

        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            matchImage = itemView.findViewById(R.id.active_matches_image);
            title = itemView.findViewById(R.id.title_text);
            subtitle = itemView.findViewById(R.id.subtitle_text);
            time = itemView.findViewById(R.id.time_text);
            status = itemView.findViewById(R.id.active_status_cardView);

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


