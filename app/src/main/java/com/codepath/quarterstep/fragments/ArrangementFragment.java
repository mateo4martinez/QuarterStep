package com.codepath.quarterstep.fragments;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.codepath.quarterstep.R;
import com.codepath.quarterstep.adapters.NotesAdapter;
import com.codepath.quarterstep.models.Note;
import com.codepath.quarterstep.utils.Constants;
import com.codepath.quarterstep.utils.ScreenSlidePageFragment;
import com.codepath.quarterstep.utils.SongPlayer;
import com.codepath.quarterstep.views.ArrangementView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ArrangementFragment extends ScreenSlidePageFragment {
    public static final String TAG = "ArrangementFragment";

    private ArrangementView avNotes;
    private NotesAdapter notesAdapter;
    private List<List<Note>> grid;
    private List<Note> adapterArray;
    private SoundPool soundPool;
    private SongPlayer songPlayer;

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

        songPlayer = buildSongPlayer();
        songPlayer.loadSounds(getActivity(), Constants.SOUNDS_ARRAY);

        avNotes.generateGrid();
        grid = avNotes.getGrid();

        notesAdapter = new NotesAdapter(getActivity(), grid);
        adapterArray = notesAdapter.getNotes();

        avNotes.setAdapter(notesAdapter);
        avNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note note = adapterArray.get(position);
                note.selected(getActivity());
                if (!note.getFlag() && note.isPlayable()) {
                    songPlayer.playOneNote(note.getNoteName());
                }

                notesAdapter.notifyDataSetChanged();
            }
        });
    }

    private SongPlayer buildSongPlayer() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(Constants.NUM_NOTES)
                .setAudioAttributes(audioAttributes)
                .build();

        AudioManager audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);

        return new SongPlayer(getActivity(), soundPool);
    }
}