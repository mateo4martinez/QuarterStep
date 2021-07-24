package com.codepath.quarterstep.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
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
    private Song song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        ivImage = findViewById(R.id.ivImage);
        ibPlay = findViewById(R.id.ibPlay);
        etSongName = findViewById(R.id.etSongName);
        tvCreatedAt = findViewById(R.id.tvCreatedAt);
        etCharacteristics = findViewById(R.id.etCharacteristics);
        etCaption = findViewById(R.id.etCaption);
        btnFavorite = findViewById(R.id.btnFavorite);
        btnShare = findViewById(R.id.btnShare);

        // get songString from intent
        //List<List<Note>> rawSong = Song.convertToRawSong() // pass in songString
        // put rawSong into 'song' object
        // build new songPlayer, loadsounds, add song into it
        // set onclicklistener so when imagebutton clicked, play song

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String characteristics = etCharacteristics.getText().toString();
                String caption = etCaption.getText().toString();
                ParseUser currentUser = ParseUser.getCurrentUser();
                // extract and set song characteristics here
                // save song in parse database here
                savePost(characteristics, caption, currentUser, ""); // send songString here
            }
        });
    }

    private void savePost(String characteristics, String caption, ParseUser currentUser, String songString) {
        Post post = new Post();
        post.setCharacteristics(characteristics);
        post.setCaption(caption);
        post.setSong(songString);
        post.setUser(currentUser);
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(ShareActivity.this, "Error while saving!", Toast.LENGTH_SHORT).show();
                    return;
                }
                finish();
            }
        });
    }
}