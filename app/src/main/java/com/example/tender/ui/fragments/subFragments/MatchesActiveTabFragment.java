package com.example.tender.ui.fragments.subFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tender.R;
import com.example.tender.model.ActiveMatches;
import com.example.tender.model.User;
import com.example.tender.utils.adapter.recyclerAdapter.MatchesActiveRvAdapter;
import com.todkars.shimmer.ShimmerAdapter;
import com.todkars.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;


public class MatchesActiveTabFragment extends Fragment {

    private ArrayList<ActiveMatches> activeMatches;
    private ShimmerRecyclerView recyclerView;

    public MatchesActiveTabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activeMatches = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_matches_active_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.active_tab_frag_recyclerView);

        setupUserInfo();
        setAdapter();
        //recyclerView.showShimmer();

    }

    public void setAdapter(){
        MatchesActiveRvAdapter matchesActiveAdapter = new MatchesActiveRvAdapter(activeMatches);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(matchesActiveAdapter);

    }

    /**
     * Dummy data set
     */
    private void setupUserInfo(){
        activeMatches.add(new ActiveMatches(
                "https://cdn.stocksnap.io/img-thumbs/960w/macro-flower_LBA8TQASNP.jpg",
                "Match 1",
                "Awesome lunch",
                "10.30 a.m.",
                0
        ));
        activeMatches.add(new ActiveMatches(
                "https://cdn.stocksnap.io/img-thumbs/960w/macro-flower_LBA8TQASNP.jpg",
                "Match 2",
                "Awesome Dinner",
                "7.30 p.m.",
                1
        ));
        activeMatches.add(new ActiveMatches(
                "https://cdn.stocksnap.io/img-thumbs/960w/macro-flower_LBA8TQASNP.jpg",
                "Match 3",
                "Awesome breakfast",
                "8.30 a.m.",
                1
        ));

    }
}
