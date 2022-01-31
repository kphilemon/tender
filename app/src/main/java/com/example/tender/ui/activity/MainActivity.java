package com.example.tender.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tender.R;
import com.example.tender.ui.fragments.SwipeFoodFragment;
import com.example.tender.utils.adapter.viewPagerAdapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigationView;
    public static String FRAGMENT_OPENED_KEY = "fragment_open";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.main_view_pager);
        bottomNavigationView = findViewById(R.id.main_bottom_nav);
        bottomNavigationView.setItemIconTintList(null);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setUserInputEnabled(false);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.main_matches_nav:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.main_friends_nav:
                        viewPager.setCurrentItem(1);
                        break;
                }
                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        Fragment frag = getSupportFragmentManager().findFragmentByTag(FRAGMENT_OPENED_KEY);
        if (frag != null)
            getSupportFragmentManager().beginTransaction().remove(frag).commitNowAllowingStateLoss();
        super.onBackPressed();
    }
}