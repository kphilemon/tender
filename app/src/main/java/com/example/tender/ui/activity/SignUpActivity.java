package com.example.tender.ui.activity;


import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tender.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class SignUpActivity extends AppCompatActivity {

    TextInputLayout textInputLayout;
    TextInputEditText user;

    Button confirm;

    boolean allgood = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        confirm = findViewById(R.id.confirm_button);
        textInputLayout = (TextInputLayout) findViewById(R.id.textinputlayout);
        user = (TextInputEditText) findViewById(R.id.username);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

        TextView view = findViewById(R.id.textview2);
        String text = "Pick wisely because once you get a name, you\n" +
                "can't change it.";

        SpannableString ss = new SpannableString(text);
        StyleSpan bold = new StyleSpan(Typeface.BOLD);
        ss.setSpan(bold, 44, 58, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.setText(ss);

        // toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }

    public void validate(){
        if(!validusername()){
            return;
        }
    }

    private boolean validusername() {

        String val = textInputLayout.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            textInputLayout.setError("Field cannot be empty");
            return false;
        } else if (val.length() < 7) {
            textInputLayout.setError("Minimum 7 characters required");
            return false;
        } else if (isStringOnlyAlphabet(val) == false) {
            textInputLayout.setError("Invalid Username");
            return false;
        } else if (val.length() > 25) {
            textInputLayout.setError("Username is too large");
            return false;
        } else {
            textInputLayout.setError(null);
            textInputLayout.setErrorEnabled(false);
            return true;
        }

    }


    private static boolean isStringOnlyAlphabet(String str) {
        return ((str != null)
                && (!str.equals(""))
                && (str.matches("^[a-zA-Z_.]*$")));
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

