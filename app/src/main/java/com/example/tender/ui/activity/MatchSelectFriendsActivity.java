package com.example.tender.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tender.R;
import com.example.tender.model.User;
import com.example.tender.model.UserWrapper;
import com.example.tender.ui.RecyclerViewInterface;
import com.example.tender.utils.adapter.recyclerAdapter.FriendsListAdapter;
import com.example.tender.utils.adapter.recyclerAdapter.SelectedFriendsAdapter;
import com.example.tender.utils.appUtils.AppUtils;
import com.google.android.material.divider.MaterialDivider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.todkars.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;

public class MatchSelectFriendsActivity extends AppCompatActivity {
    private ArrayList<UserWrapper> allFriends;
    private ShimmerRecyclerView allFriendsRecyclerView;
    private FriendsListAdapter allFriendsAdapter;

    private ArrayList<UserWrapper> selectedFriends;
    private RecyclerView selectedFriendsRecyclerView;
    private SelectedFriendsAdapter selectedFriendsAdapter;

    private Button nextButton;
    private MaterialDivider divider;
    private TextView emptyView;

    private FirebaseFirestore db;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_select_friends);

        db = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        setupToolbar();

        allFriends = new ArrayList<>();
        allFriendsRecyclerView = findViewById(R.id.all_friends_recycler_view);
        allFriendsAdapter = new FriendsListAdapter(this, allFriends, new RecyclerViewInterface() {
            @Override
            public void onItemClick(int position) {
                selectFriend(position);
            }

            @Override
            public void onItemLongClick(int position) {
            }
        });
        allFriendsRecyclerView.setAdapter(allFriendsAdapter);
        allFriendsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        allFriendsRecyclerView.setItemAnimator(new DefaultItemAnimator());

        selectedFriends = new ArrayList<>();
        selectedFriendsRecyclerView = findViewById(R.id.selected_friends_recycler_view);
        selectedFriendsAdapter = new SelectedFriendsAdapter(this, selectedFriends, new RecyclerViewInterface() {
            @Override
            public void onItemClick(int position) {
                unselectFriend(position);
            }

            @Override
            public void onItemLongClick(int position) {
            }
        });
        selectedFriendsRecyclerView.setAdapter(selectedFriendsAdapter);
        selectedFriendsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        selectedFriendsRecyclerView.setItemAnimator(new DefaultItemAnimator());

        nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(v -> navigateNext());
        divider = findViewById(R.id.divider);
        emptyView = findViewById(R.id.empty_view);

        loadAllFriends();
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

    private void selectFriend(int pos) {
        UserWrapper user = allFriends.remove(pos);
        selectedFriends.add(user);
        updateUI();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void unselectFriend(int pos) {
        UserWrapper user = selectedFriends.remove(pos);
        allFriends.add(user);
        updateUI();
    }

    private void loadAllFriends() {
        allFriendsRecyclerView.showShimmer();
        db.collection("users").document(firebaseUser.getUid())
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (snapshot.exists()) {
                        User user = snapshot.toObject(User.class);
                        assert user != null;

                        if (user.getFriends() == null || user.getFriends().size() == 0) {
                            allFriendsRecyclerView.hideShimmer();
                            updateUI();
                            return;
                        }

                        db.collection("users").whereIn(FieldPath.documentId(), user.getFriends())
                                .get()
                                .addOnSuccessListener(queryDocumentSnapshots -> {
                                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                        User u = documentSnapshot.toObject(User.class);
                                        allFriends.add(new UserWrapper(documentSnapshot.getId(), u));
                                    }
                                    allFriendsRecyclerView.hideShimmer();
                                    updateUI();
                                })
                                .addOnFailureListener(e1 -> {
                                    Log.w("TAG", "Load users from ids failed.", e1);
                                    allFriendsRecyclerView.hideShimmer();
                                    updateUI();
                                });
                    }
                })
                .addOnFailureListener(e1 -> {
                    Log.w("TAG", "error loading user.", e1);
                    allFriendsRecyclerView.hideShimmer();
                    updateUI();
                });
    }

    private void navigateNext() {
        if (selectedFriends.size() == 0) {
            AppUtils.toast(this, "Please select at least one friend to begin the matching with.");
            return;
        }

        Intent intent = new Intent(this, MatchEnterNameActivity.class);
        intent.putExtra("USERS", selectedFriends);
        startActivity(intent);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateUI() {
        divider.setVisibility(selectedFriends.size() > 0 ? View.VISIBLE : View.GONE);
        emptyView.setVisibility(allFriends.size() == 0 && selectedFriends.size() == 0 ? View.VISIBLE : View.GONE);
        allFriendsAdapter.notifyDataSetChanged();
        selectedFriendsAdapter.notifyDataSetChanged();
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
