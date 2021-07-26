package com.codepath.quarterstep.utils;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import com.codepath.quarterstep.R;

import java.util.Map;
import static java.util.Map.entry;

public class Constants {
    // Ints
        public static final int NUM_NOTES = 24;
        public static final int NUM_BEATS = 17;
        public static final int NUM_ROWS = NUM_NOTES;
        public static final int NUM_COLS = NUM_BEATS;
        public static final long NOTE_DELAY = 400;

    // Strings
        public static final String STRING_KEY = "songString";

    // Note Names
        // Octave 3
        public static final String A3 = "A3";
        public static final String A_3 = "A#3";
        public static final String B3 = "B3";

        // Octave 4
        public static final String C4 = "C4";
        public static final String C_4 = "C#4";
        public static final String D4 = "D4";
        public static final String D_4 = "D#4";
        public static final String E4 = "E4";
        public static final String F4 = "F4";
        public static final String F_4 = "F#4";
        public static final String G4 = "G4";
        public static final String G_4 = "G#4";
        public static final String A4 = "A4";
        public static final String A_4 = "A#4";
        public static final String B4 = "B4";

        // Octave 5
        public static final String C5 = "C5";
        public static final String C_5 = "C#5";
        public static final String D5 = "D5";
        public static final String D_5 = "D#5";
        public static final String E5 = "E5";
        public static final String F5 = "F5";
        public static final String F_5 = "F#5";
        public static final String G5 = "G5";
        public static final String G_5 = "G#5";

        // Inactive String
        public static final String NOTE_INACTIVE = "inactive";

    // Sounds
        // Sounds Array
            public static final int[] SOUNDS_ARRAY = new int[]{
                R.raw.a3, R.raw.a_3, R.raw.b3, R.raw.c4, R.raw.c_4, R.raw.d4, R.raw.d_4, R.raw.e4,
                R.raw.f4, R.raw.f_4, R.raw.g4, R.raw.g_4, R.raw.a4, R.raw.a_4, R.raw.b4,
                R.raw.c5, R.raw.c_5, R.raw.d5, R.raw.d_5, R.raw.e5, R.raw.f5, R.raw.f_5, R.raw.g5,
                R.raw.g_5
        };

        // Sounds Array indices
            //Octave 3
            public static final int A3_INDEX = 0;
            public static final int A_3_INDEX = 1;
            public static final int B3_INDEX = 2;

            //Octave 4
            public static final int C4_INDEX = 3;
            public static final int C_4_INDEX = 4;
            public static final int D4_INDEX = 5;
            public static final int D_4_INDEX = 6;
            public static final int E4_INDEX = 7;
            public static final int F4_INDEX = 8;
            public static final int F_4_INDEX = 9;
            public static final int G4_INDEX = 10;
            public static final int G_4_INDEX = 11;
            public static final int A4_INDEX = 12;
            public static final int A_4_INDEX = 13;
            public static final int B4_INDEX = 14;

            //Octave 5
            public static final int C5_INDEX = 15;
            public static final int C_5_INDEX = 16;
            public static final int D5_INDEX = 17;
            public static final int D_5_INDEX = 18;
            public static final int E5_INDEX = 19;
            public static final int F5_INDEX = 20;
            public static final int F_5_INDEX = 21;
            public static final int G5_INDEX = 22;
            public static final int G_5_INDEX = 23;

        // Sounds Map
        public static final Map<String, Integer> SOUNDS_MAP = Map.ofEntries(
                // Octave 3
                entry(A3, A3_INDEX),
                entry(A_3, A_3_INDEX),
                entry(B3, B3_INDEX),

                // Octave 4
                entry(C4, C4_INDEX),
                entry(C_4, C_4_INDEX),
                entry(D4, D4_INDEX),
                entry(D_4, D_4_INDEX),
                entry(E4, E4_INDEX),
                entry(F4, F4_INDEX),
                entry(F_4, F_4_INDEX),
                entry(G4, G4_INDEX),
                entry(G_4, G_4_INDEX),
                entry(A4, A4_INDEX),
                entry(A_4, A_4_INDEX),
                entry(B4, B4_INDEX),

                // Octave 5
                entry(C5, C5_INDEX),
                entry(C_5, C_5_INDEX),
                entry(D5, D5_INDEX),
                entry(D_5, D_5_INDEX),
                entry(E5, E5_INDEX),
                entry(F5, F5_INDEX),
                entry(F_5, F_5_INDEX),
                entry(G5, G5_INDEX),
                entry(G_5, G_5_INDEX)
        );

    // Row Map
    public static final Map<Integer, String> ROW_MAP = Map.ofEntries(
            // Octave 3
            entry(NUM_ROWS - 1 - A3_INDEX, A3),
            entry(NUM_ROWS - 1 - A_3_INDEX, A_3),
            entry(NUM_ROWS - 1 - B3_INDEX, B3),

            // Octave 4
            entry(NUM_ROWS - 1 - C4_INDEX, C4),
            entry(NUM_ROWS - 1 - C_4_INDEX, C_4),
            entry(NUM_ROWS - 1 - D4_INDEX, D4),
            entry(NUM_ROWS - 1 - D_4_INDEX, D_4),
            entry(NUM_ROWS - 1 - E4_INDEX, E4),
            entry(NUM_ROWS - 1 - F4_INDEX, F4),
            entry(NUM_ROWS - 1 - F_4_INDEX, F_4),
            entry(NUM_ROWS - 1 - G4_INDEX, G4),
            entry(NUM_ROWS - 1 - G_4_INDEX, G_4),
            entry(NUM_ROWS - 1 - A4_INDEX, A4),
            entry(NUM_ROWS - 1 - A_4_INDEX, A_4),
            entry(NUM_ROWS - 1 - B4_INDEX, B4),

            // Octave 5
            entry(NUM_ROWS - 1 - C5_INDEX, C5),
            entry(NUM_ROWS - 1 - C_5_INDEX, C_5),
            entry(NUM_ROWS - 1 - D5_INDEX, D5),
            entry(NUM_ROWS - 1 - D_5_INDEX, D_5),
            entry(NUM_ROWS - 1 - E5_INDEX, E5),
            entry(NUM_ROWS - 1 - F5_INDEX, F5),
            entry(NUM_ROWS - 1 - F_5_INDEX, F_5),
            entry(NUM_ROWS - 1 - G5_INDEX, G5),
            entry(NUM_ROWS - 1 - G_5_INDEX, G_5)
    );

    // Color Map
    public static final Map<String, Integer> COLOR_MAP = Map.ofEntries(
            // Octave 3
            entry(A3, R.color.A3),
            entry(A_3, R.color.A_3),
            entry(B3, R.color.B3),

            // Octave 4
            entry(C4, R.color.C4),
            entry(C_4, R.color.C_4),
            entry(D4, R.color.D4),
            entry(D_4, R.color.D_4),
            entry(E4, R.color.E4),
            entry(F4, R.color.F4),
            entry(F_4, R.color.F_4),
            entry(G4, R.color.G4),
            entry(G_4, R.color.G_4),
            entry(A4, R.color.A4),
            entry(A_4, R.color.A_4),
            entry(B4, R.color.B4),

            // Octave 5
            entry(C5, R.color.C5),
            entry(C_5, R.color.C_5),
            entry(D5, R.color.D5),
            entry(D_5, R.color.D_5),
            entry(E5, R.color.E5),
            entry(F5, R.color.F5),
            entry(F_5, R.color.F_5),
            entry(G5, R.color.G5),
            entry(G_5, R.color.G_5),

            // Inactive color
            entry(NOTE_INACTIVE, R.color.inactive)
    );

    // Soundpool components
    public static final AudioAttributes AUDIO_ATTRIBUTES = new AudioAttributes.Builder()
                                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                    .build();

    public static SoundPool SOUNDPOOL = new SoundPool.Builder()
                                    .setMaxStreams(NUM_NOTES)
                                    .setAudioAttributes(AUDIO_ATTRIBUTES)
                                    .build();

    public static int[] SOUNDS;

    public static void LOAD_SOUNDS(Context context) {
        SOUNDS = new int[NUM_NOTES];
        for (int i = 0; i < SOUNDS_ARRAY.length; i++) {
            SOUNDS[i] = SOUNDPOOL.load(context, SOUNDS_ARRAY[i], 1);
        }

        SOUNDPOOL.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                Log.i("SOUNDPOOL", "Loading complete!" + context.getClass().getSimpleName());
            }
        });
    }

    public static AudioManager AUDIO_MANAGER;

    public static void SET_AUDIO_MANAGER(Context context) {
        AUDIO_MANAGER = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        AUDIO_MANAGER.setStreamVolume(AudioManager.STREAM_MUSIC, AUDIO_MANAGER.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
    }
}
