package com.example.tender.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tender.R;
import com.example.tender.model.AddFriend;
import com.example.tender.ui.RecyclerViewInterface;
import com.example.tender.utils.adapter.recyclerAdapter.DiscoverNewFriendsAdapter;
import com.example.tender.utils.appUtils.AppUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.todkars.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;

public class DiscoverFriendsActivity extends AppCompatActivity implements RecyclerViewInterface {

    private ShimmerRecyclerView recyclerView;
    private SearchView searchView;
    DiscoverNewFriendsAdapter discoverNewFriendsAdapter;
    ArrayList<AddFriend> discoverFriendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_friends);

        setupToolBar();
        discoverFriendList = new ArrayList<AddFriend>();

        recyclerView = findViewById(R.id.discover_friends_recyclerView);
        setupDiscoverFriendsList();
        //setAdapter();

    }

    private void setupToolBar() {

        AppBarLayout toolbar = findViewById(R.id.discover_friends_toolbar);
        searchView = toolbar.findViewById(R.id.userNameSearchView);
        searchView.setVisibility(View.VISIBLE);

        ImageView backButtonImage = toolbar.findViewById(R.id.back_arrow_icon);

        backButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        /**
         * Perform search filter
         * but something weird happening to the
         * list item of add friend list items
         */
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                discoverNewFriendsAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    // Dummy data
    private void setupDiscoverFriendsList() {
        discoverFriendList.add(new AddFriend("Spider-man",true));
        discoverFriendList.add(new AddFriend("Spider-boy",false));
        discoverFriendList.add(new AddFriend("Iron-man",false));
        discoverFriendList.add(new AddFriend("Panther-man",false));
        discoverFriendList.add(new AddFriend("Awesome-man",false));
        discoverFriendList.add(new AddFriend("Unknown-man",false));
        discoverFriendList.add(new AddFriend("123-man",true));
        discoverFriendList.add(new AddFriend("456-man",false));
        discoverFriendList.add(new AddFriend("789-man",false));
        discoverFriendList.add(new AddFriend("Chick-man",true));
        discoverFriendList.add(new AddFriend("Fillet-man",true));

        setAdapter();

    }

    public void setAdapter() {
        discoverNewFriendsAdapter = new DiscoverNewFriendsAdapter(this, discoverFriendList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DiscoverFriendsActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(discoverNewFriendsAdapter);
    }

    @Override
    public void onItemClick(int position) {
        /**
         * Do something when user clicked on the
         * add friend icon
         */
        AppUtils.toast(
                DiscoverFriendsActivity.this,
                discoverFriendList.get(position).getName() + " is added as friend"
        );

        discoverFriendList.remove(position);
        discoverNewFriendsAdapter.notifyItemRemoved(position);

    }

    @Override
    public void onItemLongClick(int position) {
        /**
         * Ignore this method for now
         */
    }
}