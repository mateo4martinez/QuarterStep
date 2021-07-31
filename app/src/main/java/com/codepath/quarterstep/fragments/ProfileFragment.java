package com.codepath.quarterstep.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.codepath.quarterstep.R;
import com.codepath.quarterstep.activities.LoginActivity;
import com.codepath.quarterstep.activities.MainActivity;
import com.codepath.quarterstep.models.User;
import com.codepath.quarterstep.utils.Constants;
import com.codepath.quarterstep.utils.ScreenSlidePageFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.parse.ParseUser;

import org.jetbrains.annotations.NotNull;

public class ProfileFragment extends ScreenSlidePageFragment {
    public static final String TAG = "ProfileFragment";

    private Button btnLogout;
    private FirebaseAuth mAuth;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Constants.currentUser = new User();
                ParseUser.logOut();
                ParseUser currentUser = new ParseUser();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
    }
}