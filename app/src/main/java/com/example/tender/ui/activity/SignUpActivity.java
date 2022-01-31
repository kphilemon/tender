package com.example.tender.ui.activity;


import android.content.Intent;
import android.graphics.Typeface;
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
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.tender.R;
import com.example.tender.utils.appUtils.AppUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class SignUpActivity extends AppCompatActivity {

    EditText userNameTv;
    TextView validMessage;

    Button confirm;

    boolean isUserNameValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        confirm = findViewById(R.id.confirm_button);
        userNameTv = findViewById(R.id.sample_username_et);
        validMessage = findViewById(R.id.validation_message_tv);

        goToMainActivity();
        checkUserName();

        TextView view = findViewById(R.id.textview2);
        String text = "Pick wisely because once you get a name, you\n" +
                "can't change it.";

        // bold part of the text
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

    private void goToMainActivity() {
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUserNameValid){
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    AppUtils.toast(SignUpActivity.this, "Please enter a valid username");
                }
            }
        });
    }

    private void checkUserName() {

        userNameTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String val = userNameTv.getText().toString().trim();
                if (val.isEmpty()) {
                    validMessage.setText(R.string.empty_field);
                    formatValidMessage(false);
                } else if (val.length() < 7) {
                    validMessage.setText(R.string.minimum_seven_char);
                    formatValidMessage(false);
                } else if (!isStringOnlyAlphabet(val)) {
                    validMessage.setText(R.string.invalid_username);
                    formatValidMessage(false);
                } else if (val.length() > 25) {
                    validMessage.setText(R.string.username_limit_25);
                    formatValidMessage(false);
                } else if (val.length() >= 7 && val.length() <= 25 && isStringOnlyAlphabet(val)) {
                    validMessage.setText(R.string.valid_username);
                    formatValidMessage(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void formatValidMessage(boolean flag) {
        if (flag) {
            isUserNameValid = true;
            validMessage.setTextColor(getResources().getColor(R.color.green));
            userNameTv.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_valid_tick,
                    0
            );
        } else {
            isUserNameValid = false;
            validMessage.setTextColor(getResources().getColor(R.color.red));
            userNameTv.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.wrong,
                    0
            );
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

