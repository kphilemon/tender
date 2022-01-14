package com.example.tender.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.example.tender.R;

public class MainActivity extends AppCompatActivity {

    Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBtn = findViewById(R.id.main_go_btn);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUserPref();
            }
        });

    }

    public void goToUserPref() {
        Intent intent = new Intent(this, UserPreferenceActivity.class);
        intent.putExtra("tender", "Tinder for Food");
        startActivity(intent);
    }

}