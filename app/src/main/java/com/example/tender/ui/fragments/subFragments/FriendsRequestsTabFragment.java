package com.example.tender.ui.fragments.subFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tender.R;
import com.example.tender.model.User;
import com.example.tender.model.UserWrapper;
import com.example.tender.ui.RecyclerViewInterface;
import com.example.tender.utils.adapter.recyclerAdapter.FriendsListAdapter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.todkars.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;

public class FriendsRequestsTabFragment extends Fragment implements RecyclerViewInterface {
    private Context context;
    private ArrayList<UserWrapper> friendRequests;
    private FriendsListAdapter friendsListAdapter;
    private ShimmerRecyclerView recyclerView;
    private TextView emptyView;

    private FirebaseFirestore db;
    private FirebaseUser firebaseUser;
    private ListenerRegistration registration;

    public FriendsRequestsTabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        friendRequests = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends_requests_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.friends_requests_tab_recycler_view);
        friendsListAdapter = new FriendsListAdapter(context, friendRequests, this);
        recyclerView.setAdapter(friendsListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        emptyView = view.findViewById(R.id.friends_requests_tab_empty_view);
        updateUI(true, false);
        startPolling();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
        stopPolling();
    }

    @Override
    public void onItemClick(int position) {
        showAcceptFriendRequestDialog(position);
    }

    @Override
    public void onItemLongClick(int position) {

    }


    private void startPolling() {
        registration = db.collection("users").document(firebaseUser.getUid())
                .addSnapshotListener((snapshot, e) -> {
                    friendRequests.clear();

                    if (e != null) {
                        Log.w("TAG", "Listen failed.", e);
                        updateUI(false, true);
                        return;
                    }

                    if (snapshot == null || !snapshot.exists()) {
                        updateUI(false, true);
                        return;
                    }

                    User user = snapshot.toObject(User.class);
                    assert user != null;

                    if (user.getFriendRequests() == null || user.getFriendRequests().size() == 0) {
                        updateUI(false, true);
                        return;
                    }

                    db.collection("users").whereIn(FieldPath.documentId(), user.getFriendRequests())
                            .get()
                            .addOnSuccessListener(queryDocumentSnapshots -> {
                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    User u = documentSnapshot.toObject(User.class);
                                    friendRequests.add(new UserWrapper(documentSnapshot.getId(), u));
                                }
                                updateUI(false, false);
                            })
                            .addOnFailureListener(e1 -> {
                                Log.w("TAG", "Load users from ids failed.", e1);
                                updateUI(false, true);
                            });
                });
    }


    private void stopPolling() {
        if (registration != null) {
            registration.remove();
        }
    }

    private void acceptFriendRequest(String uid) {
        WriteBatch batch = db.batch();

        // remove target from self friend requests list & add to friends list
        DocumentReference self = db.collection("users").document(firebaseUser.getUid());
        batch.update(self, "friendRequests", FieldValue.arrayRemove(uid));
        batch.update(self, "friends", FieldValue.arrayUnion(uid));

        // Do the same for the new friend
        DocumentReference newFriend = db.collection("users").document(uid);
        batch.update(newFriend, "friendRequests", FieldValue.arrayRemove(firebaseUser.getUid()));
        batch.update(newFriend, "friends", FieldValue.arrayUnion(firebaseUser.getUid()));

        batch.commit()
                .addOnSuccessListener(aVoid -> {
                    Log.d("TAG", "Successfully accept friend request " + uid);
                })
                .addOnFailureListener(e -> Log.w("TAG", "Error accept friend request " + uid, e));
    }


    @SuppressLint("NotifyDataSetChanged")
    private void updateUI(boolean showShimmer, boolean showEmptyView) {
        friendsListAdapter.notifyDataSetChanged();
        if (showShimmer) recyclerView.showShimmer();
        else recyclerView.hideShimmer();
        emptyView.setVisibility(showEmptyView ? View.VISIBLE : View.GONE);
    }

    void showAcceptFriendRequestDialog(int position) {
        UserWrapper target = friendRequests.get(position);

        MaterialAlertDialogBuilder mad = new MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogMaterialTheme);
        mad.setTitle("Accept friend request");
        mad.setMessage("Confirm accepting " + target.getUser().getDisplayName() + "'s friend request?");
        mad.setPositiveButton("Confirm", (dialog, which) -> acceptFriendRequest(target.getId()));
        mad.setNegativeButton("Cancel", null);
        mad.show();
    }
}