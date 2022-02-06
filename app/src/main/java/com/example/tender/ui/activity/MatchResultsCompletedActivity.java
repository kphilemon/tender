package com.example.tender.ui.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tender.R;
import com.example.tender.model.Food;
import com.example.tender.utils.appUtils.AppUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

public class MatchResultsCompletedActivity extends AppCompatActivity {
    private String foodId;
    private String matchId;
    private String matchNameWithMembers;

    private TextView nameWithMembers;
    private ImageView foodImage;
    private TextView mostSwipeText;
    private Button markAsCompletedBtn;

    private FirebaseFirestore db;
    private FirebaseUser firebaseUser;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_results_completed);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
            return;
        }

        foodId = extras.getString("FOOD_ID");
        matchId = extras.getString("MATCH_ID");
        matchNameWithMembers = extras.getString("MATCH_NAME_WITH_MEMBERS");
        db = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        setupToolbar();

        nameWithMembers = findViewById(R.id.name_with_members);
        nameWithMembers.setText(matchNameWithMembers);
        foodImage = findViewById(R.id.image);
        mostSwipeText = findViewById(R.id.most_swipe_text);
        markAsCompletedBtn = findViewById(R.id.mark_as_completed_button);
        markAsCompletedBtn.setOnClickListener(v -> markAsCompleted());

        loadData();
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

    private void loadData() {
        db.collection("foods").document(foodId)
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (snapshot.exists()) {
                        Food food = snapshot.toObject(Food.class);
                        assert food != null;

                        mostSwipeText.setText(food.getName() + " got the most swipes!");
                        Glide.with(this).load(food.getPhotoUrl()).into(foodImage);
                    }
                })
                .addOnFailureListener(e -> {
                    AppUtils.toast(this, "Failed to load results.");
                    Log.w("TAG", "Failed to load results.", e);
                });
    }

    private void markAsCompleted() {
        WriteBatch batch = db.batch();

        DocumentReference self = db.collection("users").document(firebaseUser.getUid());
        batch.update(self, "activeMatches", FieldValue.arrayRemove(matchId));
        batch.update(self, "completedMatches", FieldValue.arrayUnion(matchId));

        batch.commit()
                .addOnSuccessListener(aVoid -> {
                    Log.d("TAG", "Successfully accept friend request " );
                    Intent intent = new Intent(MatchResultsCompletedActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> Log.w("TAG", "Error accept friend request " , e));
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
        Intent intent = new Intent(MatchResultsCompletedActivity.this, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}