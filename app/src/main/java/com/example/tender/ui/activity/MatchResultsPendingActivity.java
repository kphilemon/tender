package com.example.tender.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.tender.R;
import com.example.tender.utils.appUtils.AppUtils;

public class MatchResultsPendingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_results_pending);
        AppUtils.toast(this, "Please wait for 5 seconds while we redirect you to next page...");
        Intent newI = new Intent(MatchResultsPendingActivity.this, MatchResultsCompletedActivity.class);
        // Passing the result from previous activity to next
        newI.putExtra("MATCHES", getIntent().getBundleExtra("MATCHES"));
        new Handler().postDelayed(() -> startActivity(newI), 2000);
    }
}