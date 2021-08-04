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
import android.widget.GridView;
import android.widget.TextView;

import com.codepath.quarterstep.R;
import com.codepath.quarterstep.activities.LoginActivity;
import com.codepath.quarterstep.activities.MainActivity;
import com.codepath.quarterstep.adapters.SavesAdapter;
import com.codepath.quarterstep.models.Song;
import com.codepath.quarterstep.models.SongReference;
import com.codepath.quarterstep.models.User;
import com.codepath.quarterstep.utils.Constants;
import com.codepath.quarterstep.utils.ScreenSlidePageFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends ScreenSlidePageFragment {
    public static final String TAG = "ProfileFragment";

    private Button btnLogout;
    private TextView tvUsername;
    private TextView tvName;
    private TextView tvEmail;
    private GridView gvSongs;
    private SavesAdapter adapter;
    private List<SongReference> allSongs;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private User currentUser = Constants.currentUser;

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
        tvUsername = view.findViewById(R.id.tvUsername);
        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        gvSongs = view.findViewById(R.id.gvSongs);
        allSongs = new ArrayList<>();

        tvUsername.setText("@" + currentUser.getUsername());
        tvName.setText(currentUser.getFname() + " " + currentUser.getLname());
        tvEmail.setText(currentUser.getEmail());

        adapter = new SavesAdapter(getContext(), allSongs);
        gvSongs.setAdapter(adapter);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Constants.currentUser = new User();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        querySongs();
    }

    private void querySongs() {
        Task<QuerySnapshot> queryTask;
        queryTask = db.collection("songs")
                .whereEqualTo("user", currentUser)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get();

        queryTask.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> documents = task.getResult().getDocuments();
                    List<SongReference> lst = new ArrayList<>();
                    for (DocumentSnapshot doc: documents) {
                        lst.add(doc.toObject(SongReference.class));
                    }

                    allSongs.addAll(lst);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}