package com.codepath.quarterstep.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.quarterstep.R;
import com.codepath.quarterstep.models.Note;
import com.codepath.quarterstep.models.Post;
import com.codepath.quarterstep.models.Song;
import com.codepath.quarterstep.models.SongReference;
import com.codepath.quarterstep.models.User;
import com.codepath.quarterstep.utils.Constants;
import com.codepath.quarterstep.utils.SongPlayer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ShareActivity extends AppCompatActivity {
    public static final String TAG = "ShareActivity";

    private ImageView ivImage;
    private ImageButton ibPlay;
    private EditText etSongName;
    private TextView tvCreatedAt;
    private EditText etCharacteristics;
    private EditText etCaption;
    private Button btnFavorite;
    private Button btnShare;
    private SongPlayer songPlayer;
    private Song song;
    private String name;
    private String songString;
    private Date currentTime = Calendar.getInstance().getTime();
    private String date;
    private boolean wasSaved;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        Constants.LOAD_SOUNDS(this);

        ivImage = findViewById(R.id.ivImage);
        ibPlay = findViewById(R.id.ibPlay);
        etSongName = findViewById(R.id.etSongName);
        tvCreatedAt = findViewById(R.id.tvCreatedAt);
        etCharacteristics = findViewById(R.id.etCharacteristics);
        etCaption = findViewById(R.id.etCaption);
        btnFavorite = findViewById(R.id.btnFavorite);
        btnShare = findViewById(R.id.btnShare);

        // User can click enter and be done with edit text field instead of creating a newline
        etCharacteristics.setImeOptions(EditorInfo.IME_ACTION_DONE);
        etCharacteristics.setRawInputType(InputType.TYPE_CLASS_TEXT);
        etCaption.setImeOptions(EditorInfo.IME_ACTION_DONE);
        etCaption.setRawInputType(InputType.TYPE_CLASS_TEXT);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = dateFormat.format(currentTime);
        tvCreatedAt.setText(date);

        name = getIntent().getStringExtra(Constants.NAME_KEY);
        songString = getIntent().getStringExtra(Constants.SONG_KEY);
        wasSaved = getIntent().getBooleanExtra(Constants.SAVED_KEY, false);

        if (name.length() != 0) {
            etSongName.setText(name);
        }

        List<List<Note>> rawSong = Song.convertToRawSong(songString);
        song = new Song(rawSong);
        songPlayer = buildSongPlayer();
        songPlayer.addSong(song);

        ibPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    songPlayer.playSong();
                } catch (InterruptedException e) {
                    Log.e(TAG, "Issue with playing song.", e);
                }
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = Constants.currentUser;
                String songName = etSongName.getText().toString(); // add length error handling here

                if (!name.equals(songName) || !wasSaved) { // user changed name or has not saved song
                    saveSongFirebase(user, songString, songName);
                }

                String characteristics = etCharacteristics.getText().toString(); // add length error handling here
                String caption = etCaption.getText().toString(); // and here too

                savePostFirebase(user, songString, songName, caption, characteristics);
            }
        });
    }

    private void saveSongFirebase(User user, String songString, String songName) {
        SongReference songReference = new SongReference(user, songName, songString, date, false);

        db.collection("songs").add(songReference).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    Log.i(TAG, "Saving song to firebase success!");
                } else {
                    Log.e(TAG, "Issue with saving song.", task.getException());
                }
            }
        });
    }

    public void savePostFirebase(User user, String songString, String name, String caption, String characteristics) {
        Post postReference = new Post(user, songString, name, caption, characteristics, currentTime);

        db.collection("posts").add(postReference).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    Log.i(TAG, "Uploading post to firebase success!");
                    goMainActivity();
                } else {
                    Log.e(TAG, "Issue with uploading post.", task.getException());
                }
            }
        });
    }

    private void goMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private SongPlayer buildSongPlayer() {
        // must set audioManager here since this is a different activity
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);

        return new SongPlayer(this, Constants.SOUNDPOOL);
    }
}