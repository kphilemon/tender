package com.example.tender.utils.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.tender.ui.fragments.MainFriendsFragment;
import com.example.tender.ui.fragments.MainMatchesFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new MainFriendsFragment();
            default:
                return new MainMatchesFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
