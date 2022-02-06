package com.example.tender.ui.activity;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tender.R;
import com.example.tender.model.User;
import com.example.tender.utils.appUtils.AppUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collections;


public class SignUpActivity extends AppCompatActivity {

    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    private EditText usernameInput;
    private TextView validationMsg;
    private ProgressBar inputProgressBar;
    private Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        setupToolbar();

        inputProgressBar = findViewById(R.id.input_progress_bar);
        validationMsg = findViewById(R.id.validation_message);
        usernameInput = findViewById(R.id.username_input);
        usernameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validateUsername();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        confirmButton = findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(v -> createUserProfile());

        TextView instructions = findViewById(R.id.instructions_text);
        SpannableString ss = new SpannableString("Pick wisely because once you get a name, you can't change it.");
        ss.setSpan(new StyleSpan(Typeface.BOLD), 44, 58, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        instructions.setText(ss);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                firebaseAuth.signOut();
                googleSignInClient.signOut();
                navigateTo(SplashActivity.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        firebaseAuth.signOut();
        googleSignInClient.signOut();
        navigateTo(SplashActivity.class);
    }

    private void createUserProfile() {
        if (!validateUsername()) {
            return;
        }

        String username = usernameInput.getText().toString().trim();
        confirmButton.setEnabled(false);
        inputProgressBar.setVisibility(View.VISIBLE);

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        assert firebaseUser != null;
        User user = new User(
                username,
                firebaseUser.getDisplayName(),
                "",
                firebaseUser.getPhotoUrl().toString(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                false,
                ""
        );

        db.collection("users").whereEqualTo("username", username)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.getDocuments().size() > 0) {
                        setValidationMessage(getString(R.string.username_taken), true);
                        confirmButton.setEnabled(true);
                        inputProgressBar.setVisibility(View.INVISIBLE);
                        return;
                    }

                    setValidationMessage(getString(R.string.username_valid), false);
                    db.collection("users").document(firebaseUser.getUid())
                            .set(user)
                            .addOnSuccessListener(unused -> {
                                inputProgressBar.setVisibility(View.INVISIBLE);
                                navigateTo(MainActivity.class);
                            })
                            .addOnFailureListener(e -> {
                                AppUtils.toast(SignUpActivity.this, "Error creating user");
                                Log.w("TAG", "Error creating user", e);
                                confirmButton.setEnabled(true);
                                inputProgressBar.setVisibility(View.INVISIBLE);
                            });
                })
                .addOnFailureListener(e -> {
                    AppUtils.toast(SignUpActivity.this, "Error checking username availability");
                    Log.w("TAG", "Error checking username availability", e);
                    confirmButton.setEnabled(true);
                    inputProgressBar.setVisibility(View.INVISIBLE);
                });
    }

    private boolean validateUsername() {
        String s = usernameInput.getText().toString().trim();
        validationMsg.setText("");
        if (s.isEmpty()) {
            setValidationMessage(getString(R.string.username_empty), true);
            return false;
        }
        if (s.length() < 7) {
            setValidationMessage(getString(R.string.username_too_short), true);
            return false;
        }
        if (s.length() > 25) {
            setValidationMessage(getString(R.string.username_exceeded_char_limit), true);
            return false;
        }
        if (!s.matches("^[a-z0\\d._]+$")) {
            setValidationMessage(getString(R.string.username_invalid), true);
            return false;
        }
        return true;
    }

    private void setValidationMessage(String message, boolean hasError) {
        validationMsg.setText(message);
        validationMsg.setTextColor(getResources().getColor(hasError ? R.color.red : R.color.green));
        if (hasError) {
            usernameInput.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
        } else {
            usernameInput.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_valid_tick, 0);
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    private void navigateTo(Class<?> cls) {
        Intent intent = new Intent(SignUpActivity.this, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}

