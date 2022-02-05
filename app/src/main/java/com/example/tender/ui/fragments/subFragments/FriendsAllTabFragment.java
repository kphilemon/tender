package com.example.tender.ui.fragments.subFragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tender.R;
import com.example.tender.model.User;
import com.example.tender.ui.RecyclerViewInterface;
import com.example.tender.utils.adapter.recyclerAdapter.FriendsAllRvAdapter;
import com.example.tender.utils.appUtils.AppUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.todkars.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;

public class FriendsAllTabFragment extends Fragment implements RecyclerViewInterface {

    ArrayList<User> userList = new ArrayList<>();
    FriendsAllRvAdapter friendsAllRvAdapter;
    private ShimmerRecyclerView recyclerView;

    public FriendsAllTabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends_all_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.friends_all_tab_recyclerView);
        setupUserInfo();
        setAdapter();

    }

    public void setAdapter() {
        friendsAllRvAdapter = new FriendsAllRvAdapter(this, userList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(friendsAllRvAdapter);
    }

    /**
     * Dummy data set
     */
    private void setupUserInfo() {
//        userList.add(new User("Iron Man", "Awesome man"));
//        userList.add(new User("Spider Man", "Awesome bug"));
//        userList.add(new User("Thor", "Thunder God"));
//        userList.add(new User("Hulk", "Smasher"));
//        userList.add(new User("Hulk1", "Smasher"));
//        userList.add(new User("Hulk2", "Smasher"));
//        userList.add(new User("Hulk3", "Smasher"));
//        userList.add(new User("Hulk4", "Smasher"));
//        userList.add(new User("Hulk5", "Smasher"));
    }

    void createDialog(int position) {
        MaterialAlertDialogBuilder mad = new MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogMaterialTheme);
        mad.setTitle("Remove friend");
        mad.setMessage("Confirm unfriend " + userList.get(position).getDisplayName() + " from your friends list?");
        mad.setIcon(R.drawable.unfollow);

        mad.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userList.remove(position);
                friendsAllRvAdapter.notifyItemRemoved(position);
            }
        });

        mad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        mad.show();
    }

    @Override
    public void onItemClick(int position) {
        //createDialog(userList.get(position).getName());
        AppUtils.toast(requireContext(), "LONG PRESS TO UNFOLLOW");
    }


    @Override
    public void onItemLongClick(int position) {
        // Do something when long click on existing friends
        createDialog(position);
    }
}