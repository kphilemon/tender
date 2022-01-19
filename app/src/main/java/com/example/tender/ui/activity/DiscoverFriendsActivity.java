package com.example.tender.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tender.R;
import com.google.android.material.appbar.AppBarLayout;

public class DiscoverFriendsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_friends);

        setupToolBar();
    }

    private void setupToolBar() {
        AppBarLayout toolbar = findViewById(R.id.discover_friends_toolbar);

        SearchView searchView = toolbar.findViewById(R.id.userNameSearchView);
        ImageView backButtonImage = toolbar.findViewById(R.id.back_arrow_icon);

        backButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiscoverFriendsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

}