package com.example.tender.ui.fragments.subFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tender.R;
import com.example.tender.model.User;
import com.example.tender.utils.adapter.recyclerAdapter.FriendsAllRvAdapter;
import com.example.tender.utils.adapter.recyclerAdapter.MatchesActiveRvAdapter;
import com.todkars.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;

public class FriendsAllTabFragment extends Fragment {

    private ArrayList<User> userList;
    private ShimmerRecyclerView recyclerView;

    public FriendsAllTabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userList = new ArrayList<>();
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

    public void setAdapter(){
        FriendsAllRvAdapter friendsAllRvAdapter = new FriendsAllRvAdapter(userList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(friendsAllRvAdapter);
    }

    /**
     * Dummy data set
     */
    private void setupUserInfo(){
        userList.add(new User("Iron Man","Awesome man"));
        userList.add(new User("Spider Man","Awesome bug"));
        userList.add(new User("Thor","Thunder God"));
        userList.add(new User("Hulk","Smasher"));
        userList.add(new User("Hulk1","Smasher"));
        userList.add(new User("Hulk2","Smasher"));
        userList.add(new User("Hulk3","Smasher"));
        userList.add(new User("Hulk4","Smasher"));
        userList.add(new User("Hulk5","Smasher"));
    }
}