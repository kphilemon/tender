package com.example.tender.utils.adapter.viewPagerAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.tender.ui.fragments.subFragments.FriendsAllTabFragment;
import com.example.tender.ui.fragments.subFragments.FriendsRequestsTabFragment;

public class FriendsSubFragmentAdapter extends FragmentStateAdapter {

    public FriendsSubFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new FriendsRequestsTabFragment();
            default:
                return new FriendsAllTabFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
