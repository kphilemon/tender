package com.example.tender.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tender.R;
import com.example.tender.model.User;
import com.example.tender.model.UserWrapper;
import com.example.tender.ui.RecyclerViewInterface;
import com.example.tender.utils.adapter.recyclerAdapter.DiscoverFriendsAdapter;
import com.example.tender.utils.appUtils.AppUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class DiscoverFriendsActivity extends AppCompatActivity implements RecyclerViewInterface {

    private ArrayList<UserWrapper> users;
    private DiscoverFriendsAdapter discoverFriendsAdapter;
    private TextView emptyView;

    private FirebaseFirestore db;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_friends);

        db = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        setupToolbar();
        users = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.discover_friends_recycler_view);
        discoverFriendsAdapter = new DiscoverFriendsAdapter(this, users, this);
        recyclerView.setAdapter(discoverFriendsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(DiscoverFriendsActivity.this));
        emptyView = findViewById(R.id.discover_friends_empty_view);
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(int position) {
        UserWrapper target = users.get(position);
        if (target.isFriend()) {
            AppUtils.toast(DiscoverFriendsActivity.this, "You are already friends!");
            return;
        }
        sendFriendRequest(target.getId());
    }

    @Override
    public void onItemLongClick(int position) {

    }

    private void query(String s) {
        users.clear();
        if (s.length() == 0) {
            updateUI(false);
            return;
        }

        db.collection("users")
                .whereGreaterThanOrEqualTo("username", s)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.getDocuments().size() == 0) {
                        updateUI(true);
                        return;
                    }

                    db.collection("users").document(firebaseUser.getUid())
                            .get()
                            .addOnCompleteListener(task -> {
                                List<String> ownFriends = new ArrayList<>();
                                if (task.isSuccessful()) {
                                    User self = task.getResult().toObject(User.class);
                                    if (self != null && self.getFriends().size() > 0) {
                                        ownFriends.addAll(self.getFriends());
                                    }
                                }

                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    String uid = documentSnapshot.getId();
                                    if (uid.equals(firebaseUser.getUid())) {
                                        continue;
                                    }

                                    User u = documentSnapshot.toObject(User.class);
                                    boolean isFriend = ownFriends.contains(uid);
                                    users.add(new UserWrapper(uid, u, isFriend));
                                }

                                updateUI(users.size() == 0);
                            });
                })
                .addOnFailureListener(e -> {
                    Log.w("TAG", "Query usernames failed.", e);
                    updateUI(false);
                });
    }

    private void sendFriendRequest(String uid) {
        db.collection("users").document(uid)
                .update("friendRequests", FieldValue.arrayUnion(firebaseUser.getUid()))
                .addOnSuccessListener(aVoid -> {
                    Log.d("TAG", "Successfully sent friend request " + uid);
                    AppUtils.toast(DiscoverFriendsActivity.this, "Friend request sent");
                })
                .addOnFailureListener(e -> Log.w("TAG", "Error sending friend request " + uid, e));
    }

    private void setupToolbar() {
        AppBarLayout toolbar = findViewById(R.id.discover_friends_toolbar);
        ImageView backButtonImage = toolbar.findViewById(R.id.back_arrow_icon);
        backButtonImage.setOnClickListener(v -> onBackPressed());

        SearchView searchView = toolbar.findViewById(R.id.search_view);
        searchView.setVisibility(View.VISIBLE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                query(newText.trim());
                return false;
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateUI(boolean showEmptyView) {
        discoverFriendsAdapter.notifyDataSetChanged();
        emptyView.setVisibility(showEmptyView ? View.VISIBLE : View.GONE);
    }
}