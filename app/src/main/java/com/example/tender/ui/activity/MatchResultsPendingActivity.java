package com.example.tender.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.example.tender.R;
import com.example.tender.utils.appUtils.AppUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.todkars.shimmer.ShimmerRecyclerView;

public class MatchResultsPendingActivity extends AppCompatActivity {

    private ShimmerRecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_results_pending);

        recyclerView = findViewById(R.id.matching_results_pending_RV);
        recyclerView.showShimmer();

        setupToolBar();

        AppUtils.toast(this, "Please wait for 5 seconds while we redirect you to next page...");
        Intent newI = new Intent(MatchResultsPendingActivity.this, MatchResultsCompletedActivity.class);
        // Passing the result from previous activity to next
        newI.putExtra("MATCHES", getIntent().getBundleExtra("MATCHES"));
        new Handler().postDelayed(() -> startActivity(newI), 3000);
    }

    private void setupToolBar() {
        AppBarLayout toolbar = findViewById(R.id.match_result_pending_app_toolbar);
        ImageView backButtonImage = toolbar.findViewById(R.id.back_arrow_icon);
        backButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}