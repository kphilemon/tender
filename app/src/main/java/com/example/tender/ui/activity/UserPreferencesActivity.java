package com.example.tender.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.tender.R;
import com.example.tender.model.User;
import com.example.tender.ui.fragments.dialogs.EditAboutYouDialog;
import com.example.tender.ui.fragments.dialogs.EditDisplayNameDialog;
import com.example.tender.utils.appUtils.AppUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserPreferencesActivity extends AppCompatActivity implements EditDisplayNameDialog.DisplayNameChangeListener, EditAboutYouDialog.AboutYouChangeListener {

    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore db;

    private TextView username, displayName, aboutYou;
    private ShapeableImageView profileImage;
    private SwitchCompat notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_preferences);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        setupToolbar();

        username = findViewById(R.id.username);
        displayName = findViewById(R.id.display_name);
        aboutYou = findViewById(R.id.about_you);
        profileImage = findViewById(R.id.profile_image);
        notifications = findViewById(R.id.notification_switch);

        Button editDisplayNameButton = findViewById(R.id.edit_display_name_button);
        editDisplayNameButton.setOnClickListener(v -> showEditDisplayNameDialog());
        Button editAboutButton = findViewById(R.id.edit_about_button);
        editAboutButton.setOnClickListener(v -> showEditAboutYouDialog());
        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(v -> logout());
        Button deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(v -> deleteAccount());

        loadUserPreferences();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onDisplayNameChanged(String displayName) {
        this.displayName.setText(displayName);
        updateDisplayName(displayName);
    }

    @Override
    public void onAboutYouChanged(String aboutYou) {
        this.aboutYou.setText(aboutYou);
        updateAboutYou(aboutYou);
    }

    private void loadUserPreferences() {
        db.collection("users").document(firebaseUser.getUid())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        User user = documentSnapshot.toObject(User.class);
                        assert user != null;
                        username.setText(user.getUsername());
                        displayName.setText(user.getDisplayName());
                        aboutYou.setText(user.getAbout());
                        notifications.setChecked(user.isNotificationEnabled());
                        notifications.setOnCheckedChangeListener((compoundButton, b) -> updateNotifications(b));
                        Glide.with(UserPreferencesActivity.this).load(user.getPhotoUrl()).into(profileImage);
                    }
                });
    }

    private void logout() {
        googleSignInClient.signOut();
        firebaseAuth.signOut();
        navigateTo(SplashActivity.class);
    }

    private void deleteAccount() {
        db.collection("users").document(firebaseUser.getUid())
                .delete()
                .addOnSuccessListener(unused -> {
                    googleSignInClient.signOut().addOnSuccessListener(unused1 -> {
                        firebaseUser.delete().addOnSuccessListener(unused2 -> {
                            Log.d("TAG", "User successfully deleted!");
                            AppUtils.toast(UserPreferencesActivity.this, "User account deleted");
                            navigateTo(SplashActivity.class);
                        });
                    });
                })
                .addOnFailureListener(e -> {
                    Log.w("TAG", "Error deleting user document", e);
                    AppUtils.toast(UserPreferencesActivity.this, "Failed to delete user account");
                });
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

    private void updateDisplayName(String displayName) {
        db.collection("users").document(firebaseUser.getUid())
                .update("displayName", displayName)
                .addOnSuccessListener(aVoid -> {
                    Log.d("TAG", "Display name updated!");
                    AppUtils.toast(UserPreferencesActivity.this, "Display name updated!");
                })
                .addOnFailureListener(e -> Log.w("TAG", "Error updating display name", e));
    }

    private void updateAboutYou(String aboutYou) {
        db.collection("users").document(firebaseUser.getUid())
                .update("about", aboutYou)
                .addOnSuccessListener(aVoid -> {
                    Log.d("TAG", "About you updated!");
                    AppUtils.toast(UserPreferencesActivity.this, "About you updated!");
                })
                .addOnFailureListener(e -> Log.w("TAG", "Error updating about you", e));
    }

    private void updateNotifications(boolean enabled) {
        db.collection("users").document(firebaseUser.getUid())
                .update("notificationEnabled", enabled)
                .addOnSuccessListener(aVoid -> {
                    Log.d("TAG", "Notifications preference updated!");
                    AppUtils.toast(UserPreferencesActivity.this, "Notifications preference updated!");
                })
                .addOnFailureListener(e -> Log.w("TAG", "Error updating notifications preference", e));
    }

    public void showEditDisplayNameDialog() {
        EditDisplayNameDialog dialog = new EditDisplayNameDialog(displayName.getText().toString(), this);
        dialog.show(getSupportFragmentManager(), "Edit Name");
    }

    public void showEditAboutYouDialog() {
        EditAboutYouDialog dialog = new EditAboutYouDialog(aboutYou.getText().toString(), this);
        dialog.show(getSupportFragmentManager(), "Edit About");
    }

    private void navigateTo(Class<?> cls) {
        Intent intent = new Intent(UserPreferencesActivity.this, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}