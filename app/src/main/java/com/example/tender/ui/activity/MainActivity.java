package com.example.tender.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentContainerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tender.R;
import com.example.tender.ui.fragments.SwipeFoodFragment;
import com.example.tender.utils.adapter.viewPagerAdapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private FragmentContainerView testingFragmentMehdi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testingFragmentMehdi = findViewById(R.id.testing_fragment_mehdi);
        viewPager = findViewById(R.id.main_view_pager);
        bottomNavigationView = findViewById(R.id.main_bottom_nav);
        bottomNavigationView.setItemIconTintList(null);

//        toolbar = findViewById(R.id.main_toolbar);
//
//        setSupportActionBar(toolbar);
//        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.matches));


        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setUserInputEnabled(false);

//        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                switch (position) {
//                    case 0:
//                        bottomNavigationView.getMenu().findItem(R.id.main_matches_nav).setChecked(true);
//                        break;
//                    case 1:
//                        bottomNavigationView.getMenu().findItem(R.id.main_friends_nav).setChecked(true);
//                        break;
//                }
//            }
//        });

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

//        TODO: REMOVE THE BELOW THING: THIS IS ONLY FOR TESTING BY MEHDI
        SwipeFoodFragment fg = new SwipeFoodFragment();
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_out_right, android.R.anim.slide_in_left)
                .replace(testingFragmentMehdi.getId(), fg)
                .addToBackStack(null)
                .commit();

    }

}