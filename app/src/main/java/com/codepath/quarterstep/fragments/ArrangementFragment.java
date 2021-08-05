package com.codepath.quarterstep.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
import com.codepath.quarterstep.models.SongReference;
import com.codepath.quarterstep.models.User;
import com.codepath.quarterstep.utils.Constants;
import com.codepath.quarterstep.utils.ScreenSlidePageFragment;
import com.codepath.quarterstep.utils.SongPlayer;
import com.codepath.quarterstep.views.ArrangementView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ArrangementFragment extends ScreenSlidePageFragment {
    public static final String TAG = "ArrangementFragment";

    private ArrangementView avNotes;
    private NotesAdapter notesAdapter;
    private List<List<Note>> grid;
    private List<Note> adapterArray;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private SongPlayer songPlayer;
    private EditText etSongName;
    private Button btnPlay;
    private Button btnShare;
    private Button btnSave;
    private Button btnClear;
    private boolean wasSaved = false;
    private Intent intent;

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
        intent = new Intent(getActivity(), ShareActivity.class);

        avNotes.generateGrid();
        grid = avNotes.getGrid();

        notesAdapter = new NotesAdapter(getActivity(), grid);
        adapterArray = notesAdapter.getNotes();

        avNotes.setAdapter(notesAdapter);
        avNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note note = adapterArray.get(position);
                YoYo.with(Techniques.Pulse).duration(Constants.NOTE_DELAY).playOn(view);
                selectNote(note);

                notesAdapter.notifyDataSetChanged();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Pulse).duration(Constants.NOTE_DELAY * 2).repeat(8).playOn(btnPlay);
                Song song = new Song(avNotes.getGrid());
                songPlayer.addSong(song);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            songPlayer.playSong();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Pulse).duration(Constants.NOTE_DELAY).playOn(btnClear);
                YoYo.with(Techniques.Shake).duration(Constants.NOTE_DELAY * 2).playOn(avNotes);
                for (int i = 0; i < adapterArray.size(); i++) {
                    Note note = adapterArray.get(i);
                    if (note.isPlayable()) {
                        selectNote(note);
                    }
                }

                notesAdapter.notifyDataSetChanged();
                wasSaved = false;
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Pulse).duration(Constants.NOTE_DELAY).playOn(btnShare);
                Song song = new Song(avNotes.getGrid());
                String songString = song.convertToParseString();
                String songName = "";
                if (etSongName.getText().toString().length() != 0) { // add length error handling here
                    songName = etSongName.getText().toString();
                }
                intent.putExtra(Constants.NAME_KEY, songName);
                intent.putExtra(Constants.SONG_KEY, songString);
                intent.putExtra(Constants.SAVED_KEY, wasSaved);
                intent.putExtra(Constants.FAVORITE_KEY, false); // Send default favorite key
                wasSaved = false;
                startActivity(intent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Pulse).duration(Constants.NOTE_DELAY).playOn(btnSave);
                Song song = new Song(avNotes.getGrid());
                String songString = song.convertToParseString();
                String songName = etSongName.getText().toString(); // add length error handling here
                User user = Constants.currentUser;

                saveSong(user, songString, songName);
                wasSaved = true;
            }
        });
    }

    private void saveSong(User user, String songString, String songName) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date timestamp = Calendar.getInstance().getTime();
        String date = dateFormat.format(timestamp);

        SongReference songReference = new SongReference(user, songName, songString, date, timestamp, false);

        db.collection("songs").add(songReference).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    DocumentReference doc = task.getResult();
                    intent.putExtra(Constants.DOC_ID_KEY, doc.getId());
                    Log.i(TAG, "Saving song to firebase success!");
                } else {
                    Log.e(TAG, "Issue with saving song.", task.getException());
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