package com.example.tender.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tender.R;
import com.example.tender.model.Food;
import com.example.tender.model.FoodWrapper;
import com.example.tender.utils.adapter.recyclerAdapter.CardsSwipeAdapter;
import com.example.tender.utils.appUtils.AppUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SwipeActivity extends AppCompatActivity implements CardStackListener {
    private String matchId;
    private String matchName;
    private HashMap<String, String> matchDisplayNames;
    private List<String> swipedFoodIds;

    private ArrayList<FoodWrapper> foods;
    private CardStackView cardStackView;
    private CardStackLayoutManager manager;
    private CardsSwipeAdapter adapter;

    private FirebaseFirestore db;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
            return;
        }

        matchId = extras.getString("MATCH_ID");
        matchName = extras.getString("MATCH_NAME");
        matchDisplayNames = (HashMap<String, String>) extras.getSerializable("MATCH_DISPLAY_NAMES");
        db = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        setupToolbar();

        foods = new ArrayList<>();
        swipedFoodIds = new ArrayList<>();

        manager = new CardStackLayoutManager(this, this);
        adapter = new CardsSwipeAdapter(this, foods);
        cardStackView = findViewById(R.id.card_stack_view);
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        manager.setStackFrom(StackFrom.Bottom);
        manager.setVisibleCount(1);
        manager.setTranslationInterval(8f);
        manager.setDirections(Direction.HORIZONTAL);

        FloatingActionButton skip = findViewById(R.id.skip_button);
        FloatingActionButton like = findViewById(R.id.like_button);
        skip.setOnClickListener(v -> {
            SwipeAnimationSetting settings = new SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Left)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(new AccelerateInterpolator())
                    .build();
            manager.setSwipeAnimationSetting(settings);
            cardStackView.swipe();
        });

        like.setOnClickListener(v -> {
            SwipeAnimationSetting settings = new SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Right)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(new AccelerateInterpolator())
                    .build();
            manager.setSwipeAnimationSetting(settings);
            cardStackView.swipe();
        });

        TextView nameWithMembers = findViewById(R.id.name_with_members);
        nameWithMembers.setText(matchName + " with " + AppUtils.formatNames(matchDisplayNames, firebaseUser.getUid()));

        loadFoods();
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
    public void onCardSwiped(Direction direction) {
        int top = manager.getTopPosition();
        if (direction == Direction.Right) {
            swipedFoodIds.add(foods.get(top - 1).getId());
        }

        if (top == foods.size()) {
            uploadSwipes();
        }
    }

    @Override
    public void onCardDragging(Direction direction, float ratio) {
    }

    @Override
    public void onCardRewound() {
    }

    @Override
    public void onCardCanceled() {
    }

    @Override
    public void onCardAppeared(View view, int position) {
    }

    @Override
    public void onCardDisappeared(View view, int position) {
    }


    @SuppressLint("NotifyDataSetChanged")
    private void loadFoods() {
        db.collection("foods")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Food f = documentSnapshot.toObject(Food.class);
                        foods.add(new FoodWrapper(documentSnapshot.getId(), f));
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Log.w("TAG", "Failed to load foods", e);
                    AppUtils.toast(SwipeActivity.this, "Failed to load foods");
                });
    }

    private void uploadSwipes() {
        db.collection("matches").document(matchId)
                .update("swipes." + firebaseUser.getUid(), swipedFoodIds)
                .addOnSuccessListener(aVoid -> {
                    Log.d("TAG", "Successfully uploaded swipes");
                    Intent intent = new Intent(SwipeActivity.this, MatchResultsPendingActivity.class);
                    intent.putExtra("MATCH_ID", matchId);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Log.w("TAG", "Error uploading swipes", e);
                    AppUtils.toast(SwipeActivity.this, "Error uploading swipes");
                });
        ;
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

    private void navigateTo(Class<?> cls) {
        Intent intent = new Intent(SwipeActivity.this, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}