package com.example.tender.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tender.R;
import com.example.tender.ui.activity.MatchSelectFriendsActivity;
import com.example.tender.ui.activity.UserPreferencesActivity;
import com.example.tender.utils.adapter.viewPagerAdapter.MatchesSubFragmentAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;


public class MainMatchesFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;

    public MainMatchesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_matches, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupToolbar(view);

        Button fab = view.findViewById(R.id.frag_matches_go_btn);
        fab.setOnClickListener(v -> goToDiscoverFriends());

        tabLayout = view.findViewById(R.id.frag_matches_tab_layout);
        viewPager2 = view.findViewById(R.id.frag_matches_view_pager);

        FragmentManager fm = getParentFragmentManager();
        MatchesSubFragmentAdapter adapter = new MatchesSubFragmentAdapter(fm, getLifecycle());
        viewPager2.setAdapter(adapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        tabLayout.addTab(tabLayout.newTab().setText("Active"));
        tabLayout.addTab(tabLayout.newTab().setText("Completed"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setupToolbar(View view) {
        AppBarLayout toolbar = view.findViewById(R.id.frag_matches_toolbar);

        TextView title = toolbar.findViewById(R.id.toolbarTitle);
        title.setText(getResources().getString(R.string.matches));

        ImageView userIcon = toolbar.findViewById(R.id.user_icon);
        userIcon.setVisibility(View.VISIBLE);

        userIcon.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), UserPreferencesActivity.class);
            startActivity(intent);
        });
    }

    public void goToDiscoverFriends() {
        Intent intent = new Intent(requireContext(), MatchSelectFriendsActivity.class);
        startActivity(intent);
    }
}