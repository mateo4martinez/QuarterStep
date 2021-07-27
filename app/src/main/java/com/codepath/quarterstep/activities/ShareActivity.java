package com.codepath.quarterstep.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
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

import com.codepath.quarterstep.R;
import com.codepath.quarterstep.models.Note;
import com.codepath.quarterstep.models.Post;
import com.codepath.quarterstep.models.Song;
import com.codepath.quarterstep.utils.Constants;
import com.codepath.quarterstep.utils.SongPlayer;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
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
    private String songName;
    private String songString;
    private boolean wasSaved;

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

        Date currentTime = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = dateFormat.format(currentTime);
        tvCreatedAt.setText(date);

        songName = getIntent().getStringExtra(Constants.NAME_KEY);
        songString = getIntent().getStringExtra(Constants.SONG_KEY);
        wasSaved = getIntent().getBooleanExtra(Constants.SAVED_KEY, false);

        if (songName.length() != 0) {
            etSongName.setText(songName);
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
                ParseUser currentUser = ParseUser.getCurrentUser();
                String songName = etSongName.getText().toString();
                if (!wasSaved) {
                    saveSong(songString, currentUser, songName);
                }

                String characteristics = etCharacteristics.getText().toString();
                String caption = etCaption.getText().toString();
                savePost(characteristics, caption, currentUser, songName, songString);
            }
        });
    }

    private void saveSong(String songString, ParseUser currentUser, String songName) {
        Song song = new Song();
        song.setSong(songString);
        song.setUser(currentUser);
        song.setName(songName);
        song.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving song", e);
                    Toast.makeText(ShareActivity.this, "Error while saving song!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void savePost(String characteristics, String caption, ParseUser currentUser, String songName, String songString) {
        Post post = new Post();
        post.setCharacteristics(characteristics);
        post.setCaption(caption);
        post.setUser(currentUser);
        post.setSong(songString);
        post.setName(songName);
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving post", e);
                    Toast.makeText(ShareActivity.this, "Error while saving post!", Toast.LENGTH_SHORT).show();
                    return;
                }
                goMainActivity();
                finish();
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