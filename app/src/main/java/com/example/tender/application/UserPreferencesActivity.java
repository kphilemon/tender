package com.example.tender.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tender.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class UserPreferencesActivity extends AppCompatActivity implements Custom_Dialog.Custom_DialogInterFace, Custom_Dialog1.Custom_dialog{

    Button buttonShow, buttonshow1, submit;
    TextView name, about, content;
    AlertDialog dialog;

    public void openDialog(){
        Custom_Dialog custom_dialog = new Custom_Dialog();
        custom_dialog.show(getSupportFragmentManager(), "Edit Name");

    }

    public void openDialog1(){
        Custom_Dialog1 custom_dialog1 = new Custom_Dialog1();
        custom_dialog1.show(getSupportFragmentManager(), "Edit About");
    }

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
        buttonshow1 = findViewById(R.id.buttonShow1);

        name = findViewById(R.id.name);
        about = findViewById(R.id.aboutyou);
        content = findViewById(R.id.edit_name);

        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        buttonshow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog1();
            }
        });


     }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void applyTexts(String cont) {
        name.setText(cont);
    }

    @Override
    public void applyText(String cont) {
        about.setText(cont);
    }
}