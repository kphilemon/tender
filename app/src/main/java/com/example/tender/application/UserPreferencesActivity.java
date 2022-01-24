package com.example.tender.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tender.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class UserPreferencesActivity extends AppCompatActivity {

    Button buttonShow;
    TextView name, about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_preferences);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        buttonShow = findViewById(R.id.buttonShow);

        name = findViewById(R.id.name);
        about = findViewById(R.id.aboutyou);

        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditName dialogSheet = new EditName();
                dialogSheet.show(getSupportFragmentManager(), "TAG");
            }
        });


        if(getIntent().getExtras()!=null){

            String dummy = getIntent().getStringExtra("name");
            name.setText(dummy);


            String dun = getIntent().getStringExtra("about");
            about.setText(dun);


        }



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);

    }
}