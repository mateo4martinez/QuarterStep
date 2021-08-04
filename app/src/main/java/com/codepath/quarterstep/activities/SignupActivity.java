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
import android.widget.ImageButton;
import android.widget.Toast;

import com.codepath.quarterstep.R;
import com.codepath.quarterstep.models.User;
import com.codepath.quarterstep.utils.Constants;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SignupActivity extends AppCompatActivity {

    public static final String TAG = "SignupActivity";
    public static final String KEY_FIRST = "firstName";
    public static final String KEY_LAST = "lastName";

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etEmail;
    private Button btnSignup;
    private ImageButton ibBack;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnSignup = findViewById(R.id.btnSignup);
        ibBack = findViewById(R.id.ibBack);

        // User can click enter and be done with edit text fields
        changeFieldOptions();

        mAuth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Pulse).duration(Constants.NOTE_DELAY).playOn(btnSignup);
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String email = etEmail.getText().toString();
                firebaseSignup(username, firstName, lastName, email, password);
            }
        });

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Pulse).duration(Constants.NOTE_DELAY).playOn(ibBack);
                finish();
            }
        });
    }

    private void changeFieldOptions() {
        int imeOptions = EditorInfo.IME_ACTION_DONE;
        int inputType = InputType.TYPE_CLASS_TEXT;

        etFirstName.setImeOptions(imeOptions);
        etLastName.setImeOptions(imeOptions);
        etEmail.setImeOptions(imeOptions);
        etUsername.setImeOptions(imeOptions);
        etPassword.setImeOptions(imeOptions);

        etFirstName.setRawInputType(inputType);
        etLastName.setRawInputType(inputType);
        etEmail.setRawInputType(inputType);
        etUsername.setRawInputType(inputType);
    }

    private void firebaseSignup(String username, String fname, String lname, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "Signup successful!");
                            firebaseLogin(username, fname, lname, email, password);
                        } else {
                            Log.e(TAG, "Issue with signing up.", task.getException());
                        }
                    }
                });
    }

    private void firebaseLogin(String username, String fname, String lname, String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "Login successful!");

                            // add new user to database
                            Date currentTime = Calendar.getInstance().getTime();
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String date = dateFormat.format(currentTime);
                            String userID = mAuth.getCurrentUser().getUid();

                            Constants.currentUser = new User(username, fname, lname, email, date, userID);
                            db.collection("users").document(userID).set(Constants.currentUser);

                            goMainActivity();
                        } else {
                            Log.e(TAG, "Issue with login", task.getException());
                        }
                    }
                });
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}