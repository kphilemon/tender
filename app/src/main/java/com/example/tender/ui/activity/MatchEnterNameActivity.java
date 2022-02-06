package com.example.tender.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tender.R;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;


public class MatchEnterNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_enter_name);

        setupToolbar();

        Button button = findViewById(R.id.BtnStartSwiping);
        EditText editText = findViewById(R.id.ETGroupname);
        TextView textView = findViewById(R.id.TVgroupmember);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        ArrayList<String> groupmember = MatchSelectFriendsActivity.chooseusernameData;
        String show = "with ";
        for(int i = 0; i < groupmember.size();i++){
            if (i == groupmember.size()-1){
                show += groupmember.get(i);
            }
            else {
                show += groupmember.get(i);
                show += " and ";
            }
        }
        textView.setText(show);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String groupname = editText.getText().toString();
                Toast.makeText(MatchEnterNameActivity.this,"name: "+ groupname,Toast.LENGTH_LONG).show();
                startActivity(new Intent(MatchEnterNameActivity.this, SwipeActivity.class));
            }
        });

//        ActionBar backActionBar = getSupportActionBar();
//        if(backActionBar!= null){
//            backActionBar.setDisplayHomeAsUpEnabled(true);
//            backActionBar.setTitle(null);
//        }

    }

    private void setupToolbar() {
        AppBarLayout toolbar = findViewById(R.id.match_step_2_toolbar);
        SearchView searchView = toolbar.findViewById(R.id.search_view);
        ImageView backButtonImage = toolbar.findViewById(R.id.back_arrow_icon);

        backButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

//    public boolean onOptionsItemSelected(MenuItem item){
//        if(item.getItemId() == android.R.id.home){
//            this.finish();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}

