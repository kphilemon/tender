package com.example.tender.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tender.R;
import com.example.tender.model.ActiveMatches;
import com.google.android.material.appbar.AppBarLayout;

public class MatchResultsCompletedActivity extends AppCompatActivity {

    private Button completedBtn;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_results_completed);

        setupToolbar();

        completedBtn = findViewById(R.id.mark_as_completed_btn);

        Bundle b = getIntent().getBundleExtra("MATCHES");
        ActiveMatches match = new ActiveMatches(
                b.getString("matchImage"),
                b.getString("matchTitle"),
                b.getString("matchDesc"),
                b.getString("matchTime"),
                b.getInt("matchStatus")
        );
        ((TextView) findViewById(R.id.most_swipesTV)).setText(match.getMatchTitle()+" got the most swipes !");
        FrameLayout container = findViewById(R.id.swipe_item_result);
        View v = getLayoutInflater().inflate(R.layout.item_swipe_card, container, false);
        v.findViewById(R.id.right_overlay).setVisibility(View.GONE);
        v.findViewById(R.id.left_overlay).setVisibility(View.GONE);
        v.findViewById(R.id.top_overlay).setVisibility(View.GONE);
        v.findViewById(R.id.bottom_overlay).setVisibility(View.GONE);
        Glide.with(this).load(match.getMatchImage()).into(((ImageView) v.findViewById(R.id.item_image)));
        ((TextView) v.findViewById(R.id.item_label)).setText(match.getMatchTitle());
        ((TextView) v.findViewById(R.id.item_sub_label)).setText(match.getMatchDesc());
        container.addView(v);

        // navigate to main activity
        completedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatchResultsCompletedActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

    }

    private void setupToolbar() {
        AppBarLayout toolbar = findViewById(R.id.match_result_completed_app_toolbar);
        ImageView backButtonImage = toolbar.findViewById(R.id.back_arrow_icon);
        backButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}