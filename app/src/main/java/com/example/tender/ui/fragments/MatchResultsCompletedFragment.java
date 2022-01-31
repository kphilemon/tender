package com.example.tender.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tender.R;
import com.example.tender.model.ActiveMatches;

public class MatchResultsCompletedFragment extends Fragment {
    private ActiveMatches match;
    private FrameLayout container;
    public MatchResultsCompletedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            match = new ActiveMatches(
                    getArguments().getString("matchImage"),
                    getArguments().getString("matchTitle"),
                    getArguments().getString("matchDesc"),
                    getArguments().getString("matchTime"),
                    getArguments().getInt("matchStatus")
            );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match_results_completed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((TextView) view.findViewById(R.id.most_swipesTV)).setText(match.getMatchTitle()+" got the most swipes !");
        container = view.findViewById(R.id.swipe_item_result);
        View v = getLayoutInflater().inflate(R.layout.com_swipe_card_item, container, false);
        v.findViewById(R.id.right_overlay).setVisibility(View.GONE);
        v.findViewById(R.id.left_overlay).setVisibility(View.GONE);
        v.findViewById(R.id.top_overlay).setVisibility(View.GONE);
        v.findViewById(R.id.bottom_overlay).setVisibility(View.GONE);
        Glide.with(requireContext()).load(match.getMatchImage()).into(((ImageView) v.findViewById(R.id.item_image)));
        ((TextView) v.findViewById(R.id.item_label)).setText(match.getMatchTitle());
        ((TextView) v.findViewById(R.id.item_sub_label)).setText(match.getMatchDesc());
        container.addView(v);
    }
}