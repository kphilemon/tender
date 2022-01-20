package com.example.tender.utils.adapter.recyclerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tender.R;
import com.example.tender.model.User;

import java.util.ArrayList;

public class FriendsAllRvAdapter extends RecyclerView.Adapter<FriendsAllRvAdapter.MyViewHolder> {

    private final ArrayList<User> userList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView mainTv;
        private final TextView subtitleTv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mainTv = itemView.findViewById(R.id.main_text_tv);
            subtitleTv = itemView.findViewById(R.id.subtitle_text_tv);
        }
    }

    @NonNull
    @Override
    public FriendsAllRvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_row_general, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsAllRvAdapter.MyViewHolder holder, int position) {

        final User user = userList.get(position);
        holder.mainTv.setText(user.getName());
        holder.subtitleTv.setText(user.getInfo());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public FriendsAllRvAdapter(ArrayList<User> userList) {
        this.userList = userList;
    }


}
