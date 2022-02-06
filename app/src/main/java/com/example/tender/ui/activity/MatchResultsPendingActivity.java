package com.example.tender.ui.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tender.R;
import com.example.tender.model.Match;
import com.example.tender.model.SwipeStatus;
import com.example.tender.model.User;
import com.example.tender.model.UserWrapper;
import com.example.tender.utils.adapter.recyclerAdapter.SwipeStatusAdapter;
import com.example.tender.utils.appUtils.AppUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.todkars.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchResultsPendingActivity extends AppCompatActivity {
    private String matchId;

    private ArrayList<SwipeStatus> swipeStatuses;
    private SwipeStatusAdapter swipeStatusAdapter;
    private ShimmerRecyclerView recyclerView;
    private TextView nameWithMembers;
    private Button resultsButton;

    private FirebaseFirestore db;
    private FirebaseUser firebaseUser;
    private ListenerRegistration registration;

    private String mostSwipedFoodId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_results_pending);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
            return;
        }

        matchId = extras.getString("MATCH_ID");
        db = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        setupToolbar();

        swipeStatuses = new ArrayList<>();
        recyclerView = findViewById(R.id.swipe_status_recycler_view);
        swipeStatusAdapter = new SwipeStatusAdapter(this, swipeStatuses);
        recyclerView.setAdapter(swipeStatusAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultsButton = findViewById(R.id.results_button);
        resultsButton.setOnClickListener(v -> displayResults());
        nameWithMembers = findViewById(R.id.name_with_members);
        updateUI(true, false, "");
        startPolling();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                navigateTo(MainActivity.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        navigateTo(MainActivity.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopPolling();
    }

    private void startPolling() {
        registration = db.collection("matches").document(matchId)
                .addSnapshotListener((snapshot, e) -> {
                    swipeStatuses.clear();

                    if (e != null) {
                        Log.w("TAG", "Listen failed.", e);
                        updateUI(false, false, "");
                        AppUtils.toast(this, "Something went wrong");
                        return;
                    }

                    if (snapshot == null || !snapshot.exists()) {
                        updateUI(false, false, "");
                        AppUtils.toast(this, "Something went wrong");
                        return;
                    }

                    Match match = snapshot.toObject(Match.class);
                    assert match != null;

                    String title = match.getName() + " with " + AppUtils.formatNames(match.getDisplayNames(), firebaseUser.getUid());
                    boolean isAllSwipingCompleted = match.getSwipes().size() == match.getUserIds().size();
                    if (isAllSwipingCompleted) {
                        mostSwipedFoodId = mostCounts(match.getSwipes());
                    }

                    for (String userId : match.getUserIds()) {
                        String displayName = match.getDisplayNames().get(userId);
                        String photoUrl = match.getPhotoUrls().get(userId);
                        boolean hasSwiped = match.getSwipes().get(userId) != null;
                        SwipeStatus status = new SwipeStatus(displayName, hasSwiped, photoUrl);
                        swipeStatuses.add(status);
                    }
                    updateUI(false, isAllSwipingCompleted, title);
                });
    }


    private void stopPolling() {
        if (registration != null) {
            registration.remove();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateUI(boolean showShimmer, boolean showResultsButton, String title) {
        swipeStatusAdapter.notifyDataSetChanged();
        if (showShimmer) recyclerView.showShimmer();
        else recyclerView.hideShimmer();
        resultsButton.setVisibility(showResultsButton ? View.VISIBLE : View.GONE);
        nameWithMembers.setText(title);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    private String mostCounts(HashMap<String, List<String>> map) {
        HashMap<String, Integer> frequencies = new HashMap<>();

        for (List<String> l : map.values()) {
            for (String s : l) {
                if (frequencies.get(s) != null) {
                    frequencies.put(s, frequencies.get(s) + 1);
                } else {
                    frequencies.put(s, 1);
                }
            }
        }

        Map.Entry<String, Integer> maxEntry = null;
        for (Map.Entry<String, Integer> entry : frequencies.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }

        if (maxEntry == null) {
            return "";
        }
        return maxEntry.getKey();
    }

    private void displayResults() {
        Intent intent = new Intent(MatchResultsPendingActivity.this, MatchResultsCompletedActivity.class);
        intent.putExtra("FOOD_ID", mostSwipedFoodId);
        intent.putExtra("MATCH_ID", matchId);
        intent.putExtra("MATCH_NAME_WITH_MEMBERS", nameWithMembers.getText());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void navigateTo(Class<?> cls) {
        Intent intent = new Intent(MatchResultsPendingActivity.this, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}