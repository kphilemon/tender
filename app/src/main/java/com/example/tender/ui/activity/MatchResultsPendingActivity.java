package com.example.tender.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.example.tender.R;
import com.example.tender.model.ActiveMatches;
import com.example.tender.model.SwipeStatus;
import com.example.tender.utils.adapter.recyclerAdapter.MatchesActiveRvAdapter;
import com.example.tender.utils.adapter.recyclerAdapter.SwipePendingAdapter;
import com.example.tender.utils.appUtils.AppUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.todkars.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;

public class MatchResultsPendingActivity extends AppCompatActivity {

    private ShimmerRecyclerView recyclerView;
    private ArrayList<SwipeStatus> pendingList;
    private SwipePendingAdapter swipePendingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_results_pending);

        setupToolbar();

        pendingList = new ArrayList<>();

        recyclerView = findViewById(R.id.matching_results_pending_RV);

        setupDummyData();

        AppUtils.toast(this, "Please wait for 5 seconds while we redirect you to next page...");
        Intent newI = new Intent(MatchResultsPendingActivity.this, MatchResultsCompletedActivity.class);
        // Passing the result from previous activity to next
        newI.putExtra("MATCHES", getIntent().getBundleExtra("MATCHES"));
        new Handler().postDelayed(() -> startActivity(newI), 5000);
    }

    private void setupToolbar() {
        AppBarLayout toolbar = findViewById(R.id.match_result_pending_app_toolbar);
        ImageView backButtonImage = toolbar.findViewById(R.id.back_arrow_icon);
        backButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void setupDummyData(){

        pendingList.add(new SwipeStatus(null, "Philemon", false));
        pendingList.add(new SwipeStatus(null, "Prasanth", true));
        pendingList.add(new SwipeStatus(null, "Sameer", true));
        pendingList.add(new SwipeStatus(null, "Mehdi", false));
        pendingList.add(new SwipeStatus(null, "Lin", true));

        setAdapter();
    }

    public void setAdapter(){
        swipePendingAdapter = new SwipePendingAdapter(pendingList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MatchResultsPendingActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(swipePendingAdapter);
    }

}