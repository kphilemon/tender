package com.example.tender.ui.fragments.subFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.tender.R;
import com.example.tender.model.User;
import com.example.tender.model.UserWrapper;
import com.example.tender.ui.RecyclerViewInterface;
import com.example.tender.utils.adapter.recyclerAdapter.FriendsListAdapter;
import com.example.tender.utils.appUtils.AppUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.todkars.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;

public class FriendsAllTabFragment extends Fragment implements RecyclerViewInterface {
    private Context context;
    private ArrayList<UserWrapper> friends;
    private FriendsListAdapter friendsListAdapter;
    private ShimmerRecyclerView recyclerView;
    private TextView emptyView;

    private FirebaseFirestore db;
    private FirebaseUser firebaseUser;
    private ListenerRegistration registration;

    public FriendsAllTabFragment() {
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
        friends = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends_all_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.friends_all_tab_recycler_view);
        friendsListAdapter = new FriendsListAdapter(context, friends, this);
        recyclerView.setAdapter(friendsListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        emptyView = view.findViewById(R.id.friends_all_tab_empty_view);
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
        AppUtils.toast(requireContext(), "Long press to unfriend");
    }


    @Override
    public void onItemLongClick(int position) {
        showUnfriendDialog(position);
    }

    private void startPolling() {
        registration = db.collection("users").document(firebaseUser.getUid())
                .addSnapshotListener((snapshot, e) -> {
                    friends.clear();

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

                    if (user.getFriends() == null || user.getFriends().size() == 0) {
                        updateUI(false, true);
                        return;
                    }

                    db.collection("users").whereIn(FieldPath.documentId(), user.getFriends())
                            .get()
                            .addOnSuccessListener(queryDocumentSnapshots -> {
                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    User u = documentSnapshot.toObject(User.class);
                                    friends.add(new UserWrapper(documentSnapshot.getId(), u));
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

    private void unfriend(String uid) {
        db.collection("users").document(firebaseUser.getUid())
                .update("friends", FieldValue.arrayRemove(uid))
                .addOnSuccessListener(aVoid -> {
                    Log.d("TAG", "Successfully unfriend " + uid);
                })
                .addOnFailureListener(e -> Log.w("TAG", "Error unfriend " + uid, e));
    }


    @SuppressLint("NotifyDataSetChanged")
    private void updateUI(boolean showShimmer, boolean showEmptyView) {
        friendsListAdapter.notifyDataSetChanged();
        if (showShimmer) recyclerView.showShimmer();
        else recyclerView.hideShimmer();
        emptyView.setVisibility(showEmptyView ? View.VISIBLE : View.GONE);
    }

    void showUnfriendDialog(int position) {
        UserWrapper target = friends.get(position);

        MaterialAlertDialogBuilder mad = new MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogMaterialTheme);
        mad.setTitle("Remove friend");
        mad.setMessage("Confirm remove " + target.getUser().getDisplayName() + " from your friends list?");
        mad.setPositiveButton("Confirm", (dialog, which) -> unfriend(target.getId()));
        mad.setNegativeButton("Cancel", null);
        mad.show();
    }
}