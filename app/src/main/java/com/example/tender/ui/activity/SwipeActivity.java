package com.example.tender.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.SearchView;

import com.example.tender.R;
import com.example.tender.model.ActiveMatches;
import com.example.tender.utils.adapter.recyclerAdapter.CardsSwipeAdapter;
import com.example.tender.utils.appUtils.AppUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;

import java.util.Arrays;
import java.util.List;

public class SwipeActivity extends AppCompatActivity implements CardStackListener {
    private CardStackView cardStackView;
    private CardStackLayoutManager manager;
    private FloatingActionButton skip,like;
    private CardsSwipeAdapter adapter;
    private List<ActiveMatches> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        setupToolbar();

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
        manager = new CardStackLayoutManager(this,this);
        adapter = new CardsSwipeAdapter(items, this);
        cardStackView = findViewById(R.id.card_stack_view);
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        manager.setStackFrom(StackFrom.Bottom);
        manager.setVisibleCount(2);
        manager.setTranslationInterval(8f);
        manager.setDirections(Direction.HORIZONTAL);
        skip = findViewById(R.id.skip_button);
        like = findViewById(R.id.like_button);
        setupButton();
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
            AppUtils.toast(this, "You have only "+(adapter.getItemCount() - manager.getTopPosition())+" card swipes remaining");
        else if(direction == Direction.Right){
            AppUtils.toast(this, "Please wait while we redirect you to next page...");
            // A little wait of about 250ms to complete swipe card animation
            new Handler().postDelayed(() -> {
                Intent newI = new Intent(SwipeActivity.this, MatchResultsPendingActivity.class);
                // Put the match object in bundle & pass it to next activity
                newI.putExtra("MATCHES", getActiveMatchesArguments(items.get(manager.getTopPosition())));
                startActivity(newI);
            }, 250);
        }
        if(manager.getTopPosition() == adapter.getItemCount()) {
            AppUtils.toast(this, "I am sorry you are out of your choices");
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

    private void setupToolbar() {
        AppBarLayout toolbar = findViewById(R.id.swipe_activity_toolbar);
        ImageView backButtonImage = toolbar.findViewById(R.id.back_arrow_icon);

        backButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onCardRewound () {

    }

    @Override
    public void onCardCanceled () {

    }

    @Override
    public void onCardAppeared(View view, int position) {
    }

    @Override
    public void onCardDisappeared(View view, int position) {
    }
}