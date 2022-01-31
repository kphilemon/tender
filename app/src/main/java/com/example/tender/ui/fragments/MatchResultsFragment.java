package com.example.tender.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tender.R;
import com.example.tender.model.ActiveMatches;
import com.example.tender.utils.appUtils.AppUtils;

public class MatchResultsFragment extends Fragment {
    private ActiveMatches match;
    public MatchResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match_results, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        REMOVE THIS AFTER U ADDED CONTENTS TO RECYCLER VIEW
        // IMPLEMENT THE RECYCLER VIEW INSIDE MATCH RESULTS FRAGMENT
//        AND DELETE BELOW OR MOVE IT TO ONCLICK LISTENER
        AppUtils.toast(requireContext(), "Please wait for 5 seconds while we redirect you to next page...");
        MatchResultsCompletedFragment fg = new MatchResultsCompletedFragment();
        fg.setArguments(getArguments());
        new Handler().postDelayed(() -> requireActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.fragment_container_steps, fg)
                .addToBackStack(null)
                .commit(), 2000);
    }
}