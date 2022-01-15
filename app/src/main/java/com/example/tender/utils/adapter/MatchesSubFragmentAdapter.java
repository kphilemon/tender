package com.example.tender.utils.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.tender.ui.fragments.subFragments.MatchesActiveTabFragment;
import com.example.tender.ui.fragments.subFragments.MatchesCompletedTabFragment;

public class MatchesSubFragmentAdapter extends FragmentStateAdapter {

    public MatchesSubFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 1:
                return new MatchesCompletedTabFragment();
            default:
                return new MatchesActiveTabFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
