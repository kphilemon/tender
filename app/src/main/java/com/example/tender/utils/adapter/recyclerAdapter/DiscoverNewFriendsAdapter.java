package com.example.tender.utils.adapter.recyclerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tender.R;
import com.example.tender.model.AddFriend;
import com.example.tender.ui.RecyclerViewInterface;

import java.util.ArrayList;
import java.util.Collection;

public class DiscoverNewFriendsAdapter extends RecyclerView.Adapter<DiscoverNewFriendsAdapter.MyViewHolder> implements Filterable {

    private final RecyclerViewInterface recyclerViewInterface;
    ArrayList<AddFriend> newFriendsList;
    ArrayList<AddFriend> newFriendsListFull; // contains whole data

    public DiscoverNewFriendsAdapter(RecyclerViewInterface recyclerViewInterface, ArrayList<AddFriend> addFriendsList) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.newFriendsListFull = addFriendsList;
        this.newFriendsList = new ArrayList<>(newFriendsListFull);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView friendName;
        TextView friendInfo;
        ImageView addFriendIcon;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {

            super(itemView);
            friendName = itemView.findViewById(R.id.discover_friend_name);
            friendInfo = itemView.findViewById(R.id.discover_friend_info);
            addFriendIcon = itemView.findViewById(R.id.add_friend_btn);

            addFriendIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        // or use the getAdapterPosition()
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public DiscoverNewFriendsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_add_friends, parent, false);
        return new MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscoverNewFriendsAdapter.MyViewHolder holder, int position) {

        /**
         * Set the name and info
         * as preferred
         */
        AddFriend addFriend = newFriendsList.get(position);
        holder.friendName.setText(addFriend.getName());

        if (addFriend.getExisting()){
            holder.addFriendIcon.setVisibility(View.GONE);
            holder.friendInfo.setVisibility(View.VISIBLE);
            holder.friendInfo.setText("Friend");
        }
    }

    @Override
    public int getItemCount() {
        return newFriendsList.size();
    }

    @Override
    public Filter getFilter() {
        return friendsFilter;
    }

    private final Filter friendsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            ArrayList<AddFriend> filteredNewFriends = new ArrayList<>();

            if(constraint == null || constraint.length() == 0) {
                filteredNewFriends.addAll(newFriendsListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase();
                for (AddFriend addFriend : newFriendsListFull){
                    if(addFriend.getName().toLowerCase().contains(filterPattern)){
                        filteredNewFriends.add(addFriend);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredNewFriends;
            results.count = filteredNewFriends.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            newFriendsList.clear();
            newFriendsList.addAll((Collection<? extends AddFriend>) results.values);
            notifyDataSetChanged();
        }
    };

}
