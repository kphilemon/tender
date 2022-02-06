package com.example.tender.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tender.R;
import com.example.tender.model.Match;
import com.example.tender.model.User;
import com.example.tender.model.UserWrapper;
import com.example.tender.utils.appUtils.AppUtils;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MatchEnterNameActivity extends AppCompatActivity {
    private List<String> userIds;
    private HashMap<String, String> displayNames;
    private HashMap<String, String> photoUrls;

    private EditText nameInput;

    private FirebaseFirestore db;
    private FirebaseUser firebaseUser;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_enter_name);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
            return;
        }

        parseExtras((ArrayList<UserWrapper>) extras.getSerializable("USERS"));
        db = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        setupToolbar();

        nameInput = findViewById(R.id.name_input);
        TextView membersText = findViewById(R.id.members_text);
        membersText.setText("with " + AppUtils.formatNames(displayNames, ""));
        Button startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(v -> createMatching());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void parseExtras(ArrayList<UserWrapper> wrappers) {
        userIds = new ArrayList<>();
        displayNames = new HashMap<>();
        photoUrls = new HashMap<>();

        for (UserWrapper wrapper : wrappers) {
            userIds.add(wrapper.getId());
            displayNames.put(wrapper.getId(), wrapper.getUser().getDisplayName());
            photoUrls.put(wrapper.getId(), wrapper.getUser().getPhotoUrl());
        }
    }

    private void createMatching() {
        String name = nameInput.getText().toString().trim();
        if (name.isEmpty()) {
            AppUtils.toast(this, "Please enter a name");
            return;
        }

        db.collection("users").document(firebaseUser.getUid())
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (snapshot.exists()) {
                        User self = snapshot.toObject(User.class);
                        assert self != null;

                        userIds.add(firebaseUser.getUid());
                        displayNames.put(firebaseUser.getUid(), self.getDisplayName());
                        photoUrls.put(firebaseUser.getUid(), self.getPhotoUrl());
                        Match match = new Match(name, Timestamp.now(), userIds, displayNames, photoUrls, new HashMap<>());

                        db.collection("matches")
                                .add(match)
                                .addOnSuccessListener(docRef -> {
                                    WriteBatch batch = db.batch();
                                    for (String userId : userIds) {
                                        DocumentReference user = db.collection("users").document(userId);
                                        batch.update(user, "activeMatches", FieldValue.arrayUnion(docRef.getId()));
                                    }

                                    batch.commit()
                                            .addOnSuccessListener(aVoid -> {
                                                Intent intent = new Intent(MatchEnterNameActivity.this, SwipeActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                intent.putExtra("MATCH_ID", docRef.getId());
                                                intent.putExtra("MATCH_NAME", match.getName());
                                                intent.putExtra("MATCH_DISPLAY_NAMES", match.getDisplayNames());
                                                startActivity(intent);
                                                finish();
                                            })
                                            .addOnFailureListener(e -> {
                                                AppUtils.toast(MatchEnterNameActivity.this, "Failed create new matching.");
                                                Log.w("TAG", "Failed create new matching.", e);
                                            });

                                })
                                .addOnFailureListener(e -> {
                                    AppUtils.toast(MatchEnterNameActivity.this, "Failed create new matching.");
                                    Log.w("TAG", "Failed create new matching.", e);
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    AppUtils.toast(MatchEnterNameActivity.this, "Failed create new matching.");
                    Log.w("TAG", "Failed create new matching.", e);
                });
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
}

