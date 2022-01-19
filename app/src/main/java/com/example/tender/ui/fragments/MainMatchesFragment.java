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
import com.example.tender.ui.activity.DiscoverFriendsActivity;
import com.example.tender.utils.adapter.MatchesSubFragmentAdapter;
import com.example.tender.utils.appUtils.AppUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;


public class MainMatchesFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    MatchesSubFragmentAdapter matchesSubFragmentAdapter;
    Button fab;

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

        setupToolBar(view);
        clickFAB(view);

        tabLayout = view.findViewById(R.id.frag_matches_tab_layout);
        viewPager2 = view.findViewById(R.id.frag_matches_view_pager);

        FragmentManager fm  = getActivity().getSupportFragmentManager();
        matchesSubFragmentAdapter = new MatchesSubFragmentAdapter(fm, getLifecycle());
        viewPager2.setAdapter(matchesSubFragmentAdapter);

        String active_number = "(2)"; // Change dynamically
        tabLayout.addTab(tabLayout.newTab().setText("Active " + active_number));
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

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
               tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

    }

    private void setupToolBar(View view) {
        AppBarLayout toolbar = view.findViewById(R.id.frag_matches_toolbar);

        TextView title = toolbar.findViewById(R.id.toolbarTitle);
        title.setText(getResources().getString(R.string.matches));

        ImageView userIcon = toolbar.findViewById(R.id.user_icon);
        userIcon.setVisibility(View.VISIBLE);

        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.toast(requireContext(), "User icon clicked");
            }
        });

    }

    private void clickFAB(View view) {
        fab = view.findViewById(R.id.frag_matches_go_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDiscoverFriends();
                //AppUtils.toast(requireContext(), "FAB clicked");
            }
        });
    }

    /** Called when the user taps the Send button */
    public void goToDiscoverFriends() {
        Intent intent = new Intent(requireContext(), DiscoverFriendsActivity.class);
        startActivity(intent);
    }


}