package com.codepath.quarterstep.fragments;

import android.media.SoundPool;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.quarterstep.R;
import com.codepath.quarterstep.adapters.NotesAdapter;
import com.codepath.quarterstep.models.Note;
import com.codepath.quarterstep.utils.ScreenSlidePageFragment;
import com.codepath.quarterstep.views.ArrangementView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ArrangementFragment extends ScreenSlidePageFragment {
    public static final String TAG = "ArrangementFragment";

    //private ArrangementView avNames;
    private ArrangementView avNotes;
    //private NameBlocksAdapter nameBlocksAdapter;
    private NotesAdapter notesAdapter;
    private List<List<Note>> grid;
    private SoundPool soundPool;

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

        //avNames = view.findViewById(R.id.avNames);
        avNotes = view.findViewById(R.id.avNotes);

        avNotes.generateGrid();
        grid = avNotes.getGrid();

        //nameBlocksAdapter = new NameBlocksAdapter(getActivity(), Constants.NAME_BLOCKS);
        notesAdapter = new NotesAdapter(getActivity(), grid);

        //avNames.setAdapter(nameBlocksAdapter);
        avNotes.setAdapter(notesAdapter);
//
//        avNames.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return event.getAction() == MotionEvent.ACTION_MOVE;
//            }
//        });
//
//        avNotes.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return event.getAction() == MotionEvent.ACTION_MOVE;
//            }
//        });

//        AudioAttributes audioAttributes = new AudioAttributes.Builder()
//                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
//                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                .build();
//
//        soundPool = new SoundPool.Builder()
//                .setMaxStreams(3)
//                .setAudioAttributes(audioAttributes)
//                .build();

//        AudioManager audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
//        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
    }
}