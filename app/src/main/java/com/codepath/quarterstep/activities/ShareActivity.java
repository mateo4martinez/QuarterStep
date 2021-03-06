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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.quarterstep.R;
import com.codepath.quarterstep.models.Note;
import com.codepath.quarterstep.models.Post;
import com.codepath.quarterstep.models.Song;
import com.codepath.quarterstep.models.SongReference;
import com.codepath.quarterstep.models.User;
import com.codepath.quarterstep.utils.Constants;
import com.codepath.quarterstep.utils.SongPlayer;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
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
import java.util.Map;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ShareActivity extends AppCompatActivity {
    public static final String TAG = "ShareActivity";

    private ImageButton ibPlay;
    private ImageButton ibBack;
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
    private boolean isFavorite;
    private boolean wasSaved;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        Constants.LOAD_SOUNDS(this);

        ibPlay = findViewById(R.id.ibPlay);
        ibBack = findViewById(R.id.ibBack);
        etSongName = findViewById(R.id.etSongName);
        tvCreatedAt = findViewById(R.id.tvCreatedAt);
        etCharacteristics = findViewById(R.id.etCharacteristics);
        etCaption = findViewById(R.id.etCaption);
        btnFavorite = findViewById(R.id.btnFavorite);
        btnShare = findViewById(R.id.btnShare);

        // User can click enter and be done with edit text field instead of creating a newline
        changeFieldOptions();

        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        date = dateFormat.format(currentTime);
        tvCreatedAt.setText(date);

        name = getIntent().getStringExtra(Constants.NAME_KEY);
        songString = getIntent().getStringExtra(Constants.SONG_KEY);
        wasSaved = getIntent().getBooleanExtra(Constants.SAVED_KEY, false);
        isFavorite = getIntent().getBooleanExtra(Constants.FAVORITE_KEY, false);
        final boolean initialFavoriteStatus = isFavorite;

        if (name.length() != 0) {
            etSongName.setText(name);
        }

        if (isFavorite) {
            btnFavorite.setText("Remove from Favorites");
        }

        List<List<Note>> rawSong = Song.convertToRawSong(songString);
        song = new Song(rawSong);
        songPlayer = buildSongPlayer();
        songPlayer.addSong(song);

        ibPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Pulse).duration(Constants.NOTE_DELAY * 2).repeat(6).playOn(ibPlay);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            songPlayer.playSong();
                        } catch (InterruptedException e) {
                            Log.e(TAG, "Issue with playing song.", e);
                        }
                    }
                }).start();
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Pulse).duration(Constants.NOTE_DELAY).playOn(btnShare);
                User user = Constants.currentUser;
                String songName = etSongName.getText().toString();
                String characteristics = etCharacteristics.getText().toString();
                String caption = etCaption.getText().toString();

                // Error handling
                if (songName.length() > Constants.MAX_NAME_LENGTH) {
                    Toast.makeText(ShareActivity.this, "Sorry, your song name is too long.", Toast.LENGTH_LONG).show();
                    return;
                }
                if (characteristics.length() > Constants.MAX_GENRE_LENGTH) {
                    Toast.makeText(ShareActivity.this, "Sorry, your genre description is too long.", Toast.LENGTH_LONG).show();
                    return;
                }
                if (caption.length() > Constants.MAX_CAPTION_LENGTH) {
                    Toast.makeText(ShareActivity.this, "Sorry, your caption is too long.", Toast.LENGTH_LONG).show();
                    return;
                }

                // Check if name was changed or if song was not initially saved
                if (!name.equals(songName) || !wasSaved) {
                    saveSongFirebase(user, songString, songName, isFavorite);
                } else if (initialFavoriteStatus != isFavorite) {
                    updateSong(isFavorite);
                }

                savePostFirebase(user, songString, songName, caption, characteristics);
            }
        });

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Pulse).duration(Constants.NOTE_DELAY).playOn(btnFavorite);
                actionFavorite();
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

    private void saveSongFirebase(User user, String songString, String songName, boolean isFavorite) {
        SongReference songReference = new SongReference(user, songName, songString, date, currentTime, isFavorite);

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

    private void savePostFirebase(User user, String songString, String name, String caption, String characteristics) {
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

    private void updateSong(boolean isFavorite) {
        String docId = getIntent().getStringExtra(Constants.DOC_ID_KEY);
        db.collection("songs").document(docId).update(Map.of("favorite", isFavorite));
    }

    private SongPlayer buildSongPlayer() {
        // must set audioManager here since this is a different activity
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);

        return new SongPlayer(this, Constants.SOUNDPOOL);
    }

    private void actionFavorite() {
        isFavorite = !isFavorite;
        if (isFavorite) {
            btnFavorite.setText("Remove from Favorites");
        } else {
            btnFavorite.setText("Add to Favorites");
        }
    }

    private void changeFieldOptions() {
        int info = Constants.IME_ACTION_DONE;
        int inputType = Constants.INPUT_TYPE_TEXT;

        etCharacteristics.setImeOptions(info);
        etCaption.setImeOptions(info);

        etCharacteristics.setRawInputType(inputType);
        etCaption.setRawInputType(inputType);
    }

    private void goMainActivity() {
        MainActivity.mainActivity.finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}