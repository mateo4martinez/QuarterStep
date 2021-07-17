package com.codepath.quarterstep.fragments;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.codepath.quarterstep.R;
import com.codepath.quarterstep.models.Note;
import com.codepath.quarterstep.utils.ScreenSlidePageFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ArrangementFragment extends ScreenSlidePageFragment {
    public static final String TAG = "ArrangementFragment";

    private List<Note> noteNames;
    private List<Note> allNotes;
    private Button btnPlay;
    private SoundPool soundPool;
    private int sound1, sound2, sound3;
    private boolean isLoaded;

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

        btnPlay = view.findViewById(R.id.btnPlay);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(3)
                .setAudioAttributes(audioAttributes)
                .build();

        Log.i(TAG, "loading");
        sound1 = soundPool.load(getActivity(), R.raw.c4, 1);
        sound2 = soundPool.load(getActivity(), R.raw.e4, 1);
        sound3 = soundPool.load(getActivity(), R.raw.g4, 1);
        Log.i(TAG, "loaded");

        AudioManager audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                isLoaded = true;
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoaded) {
                    soundPool.play(sound1, 1, 1, 0, 0, 1);
                    soundPool.play(sound2, 1, 1, 0, 0, 1);
                    soundPool.play(sound3, 1, 1, 0, 0, 1);
                    Log.i(TAG, "playing");
                }
            }
        });
    }
}