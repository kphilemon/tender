package com.example.tender.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tender.R;
import com.example.tender.model.ActiveMatches;

public class MatchResultsCompletedActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_results_completed);
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
        View v = getLayoutInflater().inflate(R.layout.com_swipe_card_item, container, false);
        v.findViewById(R.id.right_overlay).setVisibility(View.GONE);
        v.findViewById(R.id.left_overlay).setVisibility(View.GONE);
        v.findViewById(R.id.top_overlay).setVisibility(View.GONE);
        v.findViewById(R.id.bottom_overlay).setVisibility(View.GONE);
        Glide.with(this).load(match.getMatchImage()).into(((ImageView) v.findViewById(R.id.item_image)));
        ((TextView) v.findViewById(R.id.item_label)).setText(match.getMatchTitle());
        ((TextView) v.findViewById(R.id.item_sub_label)).setText(match.getMatchDesc());
        container.addView(v);
    }
}