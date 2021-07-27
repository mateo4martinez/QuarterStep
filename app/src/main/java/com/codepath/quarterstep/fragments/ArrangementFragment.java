package com.codepath.quarterstep.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.AudioPlaybackCaptureConfiguration;
import android.media.SoundPool;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.quarterstep.R;
import com.codepath.quarterstep.activities.ShareActivity;
import com.codepath.quarterstep.adapters.NotesAdapter;
import com.codepath.quarterstep.models.Note;
import com.codepath.quarterstep.models.Song;
import com.codepath.quarterstep.utils.Constants;
import com.codepath.quarterstep.utils.ScreenSlidePageFragment;
import com.codepath.quarterstep.utils.SongPlayer;
import com.codepath.quarterstep.views.ArrangementView;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ArrangementFragment extends ScreenSlidePageFragment {
    public static final String TAG = "ArrangementFragment";

    private ArrangementView avNotes;
    private NotesAdapter notesAdapter;
    private List<List<Note>> grid;
    private List<Note> adapterArray;
    private SongPlayer songPlayer;
    private EditText etSongName;
    private Button btnPlay;
    private Button btnShare;
    private Button btnSave;
    private Button btnClear;
    private boolean wasSaved = false;

    public ArrangementFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_arrangment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        avNotes = view.findViewById(R.id.avNotes);
        etSongName = view.findViewById(R.id.etSongName);
        btnPlay = view.findViewById(R.id.btnPlay);
        btnShare = view.findViewById(R.id.btnShare);
        btnSave = view.findViewById(R.id.btnSave);
        btnClear = view.findViewById(R.id.btnClear);

        songPlayer = new SongPlayer(getActivity(), Constants.SOUNDPOOL);

        avNotes.generateGrid();
        grid = avNotes.getGrid();

        notesAdapter = new NotesAdapter(getActivity(), grid);
        adapterArray = notesAdapter.getNotes();

        avNotes.setAdapter(notesAdapter);
        avNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note note = adapterArray.get(position);
                selectNote(note);

                notesAdapter.notifyDataSetChanged();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Song song = new Song(avNotes.getGrid());
                songPlayer.addSong(song);

                try {
                    songPlayer.playSong();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < adapterArray.size(); i++) {
                    Note note = adapterArray.get(i);
                    Log.i(TAG,  note.getNoteName() + ": " + note.getCol() + ": " + String.valueOf(note.getLayout() == null));
                    if (note.isPlayable()) {
                        selectNote(note);
                    }
                }

                notesAdapter.notifyDataSetChanged();
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Song song = new Song(avNotes.getGrid());
                String songString = song.convertToParseString();
                Intent intent = new Intent(getActivity(), ShareActivity.class);
                String songName = "";
                if (etSongName.getText().toString().length() != 0) {
                    songName = etSongName.getText().toString();
                }
                intent.putExtra(Constants.NAME_KEY, songName);
                intent.putExtra(Constants.SONG_KEY, songString);
                intent.putExtra(Constants.SAVED_KEY, wasSaved);
                wasSaved = false;
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Song song = new Song(avNotes.getGrid());
                String songString = song.convertToParseString();
                String songName = etSongName.getText().toString();
                ParseUser currentUser = ParseUser.getCurrentUser();

                saveSong(songString, currentUser, songName);
                wasSaved = true;
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
                    Toast.makeText(getActivity(), "Error while saving song!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void selectNote(Note note) {
        note.selected(getActivity());
        if (!note.getFlag() && note.isPlayable()) {
            songPlayer.playOneNote(note.getNoteName());
        }
    }
}