package com.example.tender.ui.fragments;

import static com.example.tender.ui.activity.MainActivity.FRAGMENT_OPENED_KEY;

import android.graphics.Path;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.Toast;

import com.example.tender.R;
import com.example.tender.model.ActiveMatches;
import com.example.tender.utils.adapter.recyclerAdapter.CardsSwipeAdapter;
import com.example.tender.utils.appUtils.AppUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.Arrays;
import java.util.List;

public class SwipeFoodFragment extends Fragment implements CardStackListener {
    private CardStackView cardStackView;
    private CardStackLayoutManager manager;
    private FloatingActionButton skip,like;
    private CardsSwipeAdapter adapter;
    private List<ActiveMatches> items;
    public SwipeFoodFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_swipe_food, container, false);
        items = Arrays.asList(
                createMatches("China", "Kyoto", "https://images.unsplash.com/photo-1493997181344-712f2f19d87a?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=800&ixid=MXwxfDB8MXxhbGx8fHx8fHx8fA&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=600"),
                createMatches("LOL", "Shenanigans", "https://source.unsplash.com/NYyCqdBOKwc/600x800"),
                createMatches("BLAH", "Loving & tasty", "https://source.unsplash.com/buF62ewDLcQ/600x800"),
                createMatches("Brooklyn Bridge", "New York", "https://source.unsplash.com/THozNzxEP3g/600x800"),
                createMatches("Empire State Building", "New York", "https://source.unsplash.com/USrZRcRS2Lw/600x800"),
                createMatches("The statue of Liberty", "New York", "https://source.unsplash.com/PeFk7fzxTdk/600x800"),
                createMatches("Louvre Museum", "Paris", "https://source.unsplash.com/LrMWHKqilUw/600x800"),
                createMatches("Eiffel Tower", "Paris", "https://source.unsplash.com/HN-5Z6AmxrM/600x800"),
                createMatches("Big Ben", "London", "https://source.unsplash.com/CdVAUADdqEc/600x800"),
                createMatches("Great Wall of China", "China", "https://source.unsplash.com/AWh9C-QjhE4/600x800")
        );
        manager = new CardStackLayoutManager(getContext(),this);
        adapter = new CardsSwipeAdapter(items, requireContext());
        cardStackView = v.findViewById(R.id.card_stack_view);
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        manager.setStackFrom(StackFrom.Bottom);
        manager.setVisibleCount(2);
        manager.setTranslationInterval(8f);
        manager.setDirections(Direction.HORIZONTAL);
        skip = v.findViewById(R.id.skip_button);
        like = v.findViewById(R.id.like_button);
        setupButton();
        return v;
    }

    public ActiveMatches createMatches(String label, String sub_label, String img){
        return new ActiveMatches(img, label, sub_label, String.valueOf(System.currentTimeMillis()), 0);
    }

    public void setupButton() {
        skip.setOnClickListener(v -> {
            SwipeAnimationSetting settings = new SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Left)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(new AccelerateInterpolator())
                    .build();
            manager.setSwipeAnimationSetting(settings);
            cardStackView.swipe();
        });

        like.setOnClickListener(v->{
            SwipeAnimationSetting settings = new SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Right)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(new AccelerateInterpolator())
                    .build();
            manager.setSwipeAnimationSetting(settings);
            cardStackView.swipe();
        });
    }

        @Override
        public void onCardDragging (Direction direction,float ratio){

        }

        @Override
        public void onCardSwiped (Direction direction){
           if(direction == Direction.Left)
               AppUtils.toast(requireContext(), "You have only "+(adapter.getItemCount() - manager.getTopPosition())+" card swipes remaining");
           else if(direction == Direction.Right){
               AppUtils.toast(requireContext(), "Please wait while we redirect you to next page...");
               MatchResultsFragment fg = new MatchResultsFragment();
               Bundle b = getActiveMatchesArguments(items.get(manager.getTopPosition()));
               fg.setArguments(b);
               // Delay so that swipe animation is completed successfully
               new Handler().postDelayed(() -> requireActivity().getSupportFragmentManager().beginTransaction()
                       .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                       .replace(R.id.fragment_container_steps, fg)
                       .addToBackStack(null)
                       .commit(), 250);
           }
           if(manager.getTopPosition() == adapter.getItemCount()) {
               Toast.makeText(requireContext(), "I am sorry you are out of your choices", Toast.LENGTH_LONG).show();
           }
        }

    private Bundle getActiveMatchesArguments(ActiveMatches match) {
        Bundle b = new Bundle();
        b.putString("matchImage", match.getMatchImage());
        b.putString("matchTitle", match.getMatchTitle());
        b.putString("matchDesc", match.getMatchDesc());
        b.putString("matchTime", match.getMatchTime());
        b.putInt("matchStatus", 1);
        return b;
    }

    @Override
        public void onCardRewound () {

        }

        @Override
        public void onCardCanceled () {

        }

        @Override
        public void onCardAppeared (View view,int position){

        }

        @Override
        public void onCardDisappeared (View view,int position){

        }
    }

