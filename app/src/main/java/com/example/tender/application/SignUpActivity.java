package com.example.tender.application;


import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

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

        user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {




            }

            @Override
            public void afterTextChanged(Editable s) {

                String val = textInputLayout.getEditText().getText().toString().trim();


                if (val.isEmpty()) {
                    textInputLayout.setError("Field cannot be empty");
                    textInputLayout.setErrorIconDrawable(getResources().getDrawable(R.drawable.wrong));
                } else if (val.length() < 7) {
                    textInputLayout.setError("Minimum 7 characters required");
                    textInputLayout.setErrorIconDrawable(getResources().getDrawable(R.drawable.wrong));
                } else if (isStringOnlyAlphabet(val) == false) {
                    textInputLayout.setError("Invalid Username");
                    textInputLayout.setErrorIconDrawable(getResources().getDrawable(R.drawable.wrong));
                } else if (val.length() > 25) {
                    textInputLayout.setError("Username should be under 25 characters");
                    textInputLayout.setErrorIconDrawable(getResources().getDrawable(R.drawable.wrong));
                } else if(val.length()>=7 && val.length()<=25 && isStringOnlyAlphabet(val) == true ){

                    int errorColor;
                    final int version = Build.VERSION.SDK_INT;

                    //Get the defined errorColor from color resource.
                    if (version >= 23) {
                        errorColor = ContextCompat.getColor(getApplicationContext(), R.color.green);
                    } else {
                        errorColor = getResources().getColor(R.color.green);
                    }


                    String errorString = "Great name! It's not taken, so it's all yours";
                    ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(errorColor);
                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(errorString);
                    spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorString.length(), 0);
                    textInputLayout.setError(spannableStringBuilder);
                    textInputLayout.setBoxStrokeColor(getResources().getColor(R.color.gray));
                    textInputLayout.setErrorIconDrawable(getResources().getDrawable(R.drawable.correct));
                }
                else {
                    textInputLayout.setError(null);
                    textInputLayout.setErrorEnabled(false);
                }
            }
        });


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

