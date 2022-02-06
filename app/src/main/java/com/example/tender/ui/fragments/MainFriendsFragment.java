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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tender.R;
import com.example.tender.ui.activity.DiscoverFriendsActivity;
import com.example.tender.ui.activity.UserPreferencesActivity;
import com.example.tender.utils.adapter.viewPagerAdapter.FriendsSubFragmentAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

public class MainFriendsFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;

    public MainFriendsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_friends, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupToolbar(view);

        tabLayout = view.findViewById(R.id.frag_friends_tab_layout);
        viewPager2 = view.findViewById(R.id.frag_friends_view_pager);

        FragmentManager fm = getParentFragmentManager();
        FriendsSubFragmentAdapter adpater = new FriendsSubFragmentAdapter(fm, getLifecycle());
        viewPager2.setAdapter(adpater);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.addTab(tabLayout.newTab().setText("Requests"));
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
        AppBarLayout toolbar = view.findViewById(R.id.frag_friends_toolbar);

        TextView title = toolbar.findViewById(R.id.toolbarTitle);
        title.setText(getResources().getString(R.string.friends));

        ImageView userIcon = toolbar.findViewById(R.id.user_icon);
        ImageView searchIcon = toolbar.findViewById(R.id.back_arrow_icon);
        userIcon.setVisibility(View.VISIBLE);
        searchIcon.setVisibility(View.VISIBLE);

        userIcon.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), UserPreferencesActivity.class);
            startActivity(intent);
        });

        searchIcon.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), DiscoverFriendsActivity.class);
            startActivity(intent);
        });
    }
}