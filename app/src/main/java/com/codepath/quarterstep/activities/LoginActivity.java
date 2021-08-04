package com.codepath.quarterstep.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.quarterstep.R;
import com.codepath.quarterstep.models.User;
import com.codepath.quarterstep.utils.Constants;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";

    private ImageView ivLogo;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnSignup;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        // Check if firebase user is already logged in
        if (mAuth.getCurrentUser() != null) {
            String uid = mAuth.getCurrentUser().getUid();

            Task<DocumentSnapshot> loginTask = db.collection("users").document(uid).get();
            loginTask.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // convert to a POJO
                            Constants.currentUser = document.toObject(User.class);
                            goMainActivity();
                            Log.i(TAG, "Current user login successful.");
                        }
                    } else {
                        Log.e(TAG, "Issue with logging in current user.", task.getException());
                    }
                }
            });
        }

        ivLogo = findViewById(R.id.ivLogo);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        // User can click enter and be done with edit text fields
        changeFieldOptions();

        Glide.with(getApplicationContext()).load(R.drawable.qs_logo).into(ivLogo);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Pulse).duration(Constants.NOTE_DELAY).playOn(btnLogin);
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                firebaseLogin(email, password);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Pulse).duration(Constants.NOTE_DELAY).playOn(btnSignup);
                goSignupActivity();
            }
        });
    }

    private void changeFieldOptions() {
        int imeOptions = Constants.IME_ACTION_DONE;
        int inputType = Constants.INPUT_TYPE_TEXT;

        etEmail.setImeOptions(imeOptions);
        etPassword.setImeOptions(imeOptions);

        etEmail.setRawInputType(inputType);
    }

    private void firebaseLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Set logged in user to be current user
                            String uid = mAuth.getCurrentUser().getUid();
                            Task<DocumentSnapshot> userTask = db.collection("users").document(uid).get();
                            userTask.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        Constants.currentUser = document.toObject(User.class);
                                        goMainActivity();
                                    }
                                }
                            });
                        } else {
                            Log.e(TAG, "Issue with login.", task.getException());
                        }
                    }
                });
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void goSignupActivity() {
        Intent i = new Intent(this, SignupActivity.class);
        startActivity(i);
    }
}