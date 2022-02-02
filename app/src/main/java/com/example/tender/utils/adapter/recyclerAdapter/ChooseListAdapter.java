package com.example.tender.utils.adapter.recyclerAdapter;


import android.annotation.SuppressLint;
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

public class ChooseListAdapter extends RecyclerView.Adapter<ChooseListAdapter.LinearViewHolder> {
    @NonNull
    public OnItemClickListener_choose nOnItemClickListener = null;
    private final ArrayList<String> a;
    private final ArrayList<String> b;
    private final ArrayList c;
    private LayoutInflater mlayoutInflater = null;

//    String[] names = {"Ironman","Hulk","Lee","Bobb","fafat","thhh","asdf"};
//    int[] images = {R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background};

    public ChooseListAdapter(Context context, ArrayList<String> a, ArrayList<String> b, ArrayList c) {
        this.mlayoutInflater = LayoutInflater.from(context);
        this.a = a;
        this.b = b;
        this.c = c;

    }

    public ChooseListAdapter.LinearViewHolder onCreateViewHolder(ViewGroup parenet, int viewType){
        View view = mlayoutInflater.inflate(R.layout.recyc_choose_view,parenet,false);
        LinearViewHolder linearViewHolder = new LinearViewHolder(view);
        return linearViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.image_info_photo_choose.setImageResource((Integer) Matching_step1.chooseimageDatas.get(position));
        holder.text_info_name_choose.setText(Matching_step1.chooseusernameData.get(position));
        holder.text_info_sign_choose.setText(Matching_step1.chooseusersignData.get(position));

        if(null != nOnItemClickListener){

            holder.Btn_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nOnItemClickListener.onClick_choose(holder.Btn_close,position);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return a.size();
    }




    class LinearViewHolder extends RecyclerView.ViewHolder{
        private final ImageView image_info_photo_choose;
        private final TextView text_info_name_choose;
        private final TextView text_info_sign_choose;
        private final ImageView Btn_close;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            image_info_photo_choose = itemView.findViewById(R.id.info_photo_choose);
            text_info_name_choose = itemView.findViewById(R.id.info_name_choose);
            text_info_sign_choose = itemView.findViewById(R.id.info_sign_choose);
            Btn_close = itemView.findViewById(R.id.BtnClose);

        }
    }

    public interface OnItemClickListener_choose{
        void onClick_choose(View parent, int position);
    }
    public void setOnItemClickListener_choose(OnItemClickListener_choose l){
        this.nOnItemClickListener = l;
    }

}
