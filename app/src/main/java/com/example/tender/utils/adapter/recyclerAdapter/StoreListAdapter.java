package com.example.tender.utils.adapter.recyclerAdapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tender.R;
import com.example.tender.ui.activity.Matching_step1;

import java.util.ArrayList;

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.LinearViewHolder> {
    public OnItemClickListener mOnItemClickListener = null;


    private final ArrayList<String> a;
    private final ArrayList<String> b;
    private final ArrayList c;

    private LayoutInflater mlayoutInflater = null;


    public StoreListAdapter(Context context, ArrayList<String> a, ArrayList<String> b, ArrayList c){
        this.mlayoutInflater = LayoutInflater.from(context);
        this.a = a;
        this.b = b;
        this.c = c;

    }
    @NonNull
    @Override
    public StoreListAdapter.LinearViewHolder onCreateViewHolder(ViewGroup parenet, int viewType){
        View view = mlayoutInflater.inflate(R.layout.recyc_item_view,parenet,false);
        LinearViewHolder linearViewHolder = new LinearViewHolder(view);
        return linearViewHolder;
    }
//    public StoreListAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new LinearViewHolder(LayoutInflater.from(a,b,c).inflate(R.layout.recyc_item_view,parent,false));
//    }





    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {
        holder.image_info_photo.setImageResource((Integer) Matching_step1.imageDatas.get(position));
        holder.text_info_name.setText(Matching_step1.usernameData.get(position));
        holder.text_info_sign.setText(Matching_step1.usersignData.get(position));


        if(null != mOnItemClickListener){
            holder.text_info_name.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(holder.text_info_name,position);
                }
            });
            holder.text_info_sign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(holder.text_info_sign,position);
                }
            });
            holder.image_info_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(holder.image_info_photo,position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return a.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image_info_photo;
        private final TextView text_info_name;
        private final TextView text_info_sign;

        public LinearViewHolder(View itemView){
            super(itemView);
            image_info_photo = itemView.findViewById(R.id.info_photo);
            text_info_name = itemView.findViewById(R.id.info_name);
            text_info_sign = itemView.findViewById(R.id.info_sign);
        }
    }


    public interface OnItemClickListener{
        void onClick(View parent, int position);
    }

    public void setOnItemClickListener(OnItemClickListener l){
        this.mOnItemClickListener = l;
    }
}
