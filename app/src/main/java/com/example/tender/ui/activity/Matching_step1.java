package com.example.tender.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tender.R;
import com.example.tender.utils.adapter.recyclerAdapter.ChooseListAdapter;
import com.example.tender.utils.adapter.recyclerAdapter.StoreListAdapter;

import java.util.ArrayList;

public class Matching_step1 extends AppCompatActivity implements StoreListAdapter.OnItemClickListener {
    private RecyclerView rv_storelist;
    private StoreListAdapter rv_storelistAdapter = null;

    private RecyclerView rv_chooselist;
    private ChooseListAdapter chooseListAdapter = null;

    public static ArrayList<String> usernameData = null;
    public static ArrayList<String> usersignData = null;
    public static ArrayList imageDatas = null;


    public static ArrayList<String> chooseusernameData = null;
    public static ArrayList chooseimageDatas = null;
    public static ArrayList<String> chooseusersignData = null;

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Matching_step1.this);
    LinearLayoutManager linearLayoutManager_choose = new LinearLayoutManager(Matching_step1.this,RecyclerView.HORIZONTAL,false);


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching_step1);

        readuserData();

        rv_storelist = findViewById(R.id.rv_storelist);
        rv_storelist.setLayoutManager(linearLayoutManager);

        rv_storelistAdapter = new StoreListAdapter(this,usernameData,usersignData,imageDatas );
        rv_storelistAdapter.setOnItemClickListener(this);
        rv_storelist.setAdapter(rv_storelistAdapter);
        rv_storelist.setItemAnimator(new DefaultItemAnimator());

        rv_chooselist = findViewById(R.id.rv_chooselist);
        rv_chooselist.setLayoutManager(linearLayoutManager_choose);

        chooseListAdapter = new ChooseListAdapter(this,chooseusernameData,chooseusersignData,chooseimageDatas);
        chooseListAdapter.setOnItemClickListener_choose(this::onClick_choose);
        rv_chooselist.setAdapter(chooseListAdapter);
        rv_chooselist.setItemAnimator(new DefaultItemAnimator());

        ActionBar backActionBar = getSupportActionBar();
        if(backActionBar!= null){
            backActionBar.setDisplayHomeAsUpEnabled(true);
            backActionBar.setTitle(null);
        }

        Button BtnNext = findViewById(R.id.BtnNext);
        BtnNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Matching_step1.this,Matching_step2.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }

    private void readuserData() {
        usernameData = new ArrayList<String>();
        usernameData.add("Ironman");
        usernameData.add("Hulk");
        usernameData.add("Lee");
        usernameData.add("Bobb");
        usernameData.add("fafat");
        usernameData.add("thhh");
        usernameData.add("asdf");
        usernameData.add("asdf");
        usernameData.add("asdf");

        usersignData = new ArrayList<String>();
        usersignData.add("I love u 3000");
        usersignData.add("Strongest avenger");
        usersignData.add("asdfsadf");
        usersignData.add("iiugi");
        usersignData.add("vbqebfviq");
        usersignData.add("asfasdf");
        usersignData.add("sdfg");
        usersignData.add("sdfg");
        usersignData.add("sdfg");

        imageDatas = new ArrayList();
        imageDatas.add(R.drawable.ic_launcher_background);
        imageDatas.add(R.drawable.ic_launcher_background);
        imageDatas.add(R.drawable.ic_launcher_background);
        imageDatas.add(R.drawable.ic_launcher_background);
        imageDatas.add(R.drawable.ic_launcher_background);
        imageDatas.add(R.drawable.ic_launcher_background);
        imageDatas.add(R.drawable.ic_launcher_background);
        imageDatas.add(R.drawable.ic_launcher_background);
        imageDatas.add(R.drawable.ic_launcher_background);

        chooseimageDatas = new ArrayList();
        chooseusernameData = new ArrayList<String>();
        chooseusersignData = new ArrayList<String>();

    }

    @Override
    public void onClick(View parent, int position) {
        //Toast.makeText(this, "长压了第" + (position + 1) + "项", Toast.LENGTH_SHORT).show();
        chooseusernameData.add(usernameData.get(position));
        chooseimageDatas.add(imageDatas.get(position));
        chooseusersignData.add(usersignData.get(position));
        usernameData.remove(usernameData.get(position));
        usersignData.remove(usersignData.get(position));
        imageDatas.remove(imageDatas.get(position));
        chooseListAdapter.notifyDataSetChanged();
        rv_storelistAdapter.notifyDataSetChanged();

    }

    public void onClick_choose(View parent, int position) {

        //Toast.makeText(this, "长压了第" + (position + 1) + "项", Toast.LENGTH_SHORT).show();
        usernameData.add(chooseusernameData.get(position));
        imageDatas.add(chooseimageDatas.get(position));
        usersignData.add(chooseusersignData.get(position));
        chooseusernameData.remove(chooseusernameData.get(position));
        chooseimageDatas.remove(chooseimageDatas.get(position));
        chooseusersignData.remove(chooseusersignData.get(position));
        chooseListAdapter.notifyDataSetChanged();
        rv_storelistAdapter.notifyDataSetChanged();

    }
}
