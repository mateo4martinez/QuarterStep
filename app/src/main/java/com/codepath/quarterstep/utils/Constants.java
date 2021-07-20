package com.codepath.quarterstep.utils;

import com.codepath.quarterstep.R;

import java.util.Map;
import static java.util.Map.entry;

public class Constants {
    // Ints
        public static final int NUM_NOTES = 24;
        public static final int NUM_BEATS = 16;
        public static final int NUM_ROWS = NUM_NOTES;
        public static final int NUM_COLS = NUM_BEATS;

    // Note Names
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
        public static final String A5 = "A5";
        public static final String A_5 = "A#5";
        public static final String B5 = "B5";

    // Sounds
        // Sounds Array
            public static final int[] SOUNDS_ARRAY = new int[]{
                R.raw.c4, R.raw.c_4, R.raw.d4, R.raw.d_4, R.raw.e4, R.raw.f4, R.raw.f_4, R.raw.g4,
                R.raw.g_4, R.raw.a4, R.raw.a_4, R.raw.b4,
                R.raw.c5, R.raw.c_5, R.raw.d5, R.raw.d_5, R.raw.e5, R.raw.f5, R.raw.f_5, R.raw.g5,
                R.raw.g_5, R.raw.a5, R.raw.a_5, R.raw.b5
        };

        // Sounds Array indices
            //Octave 4
            public static final int C4_INDEX = 0;
            public static final int C_4_INDEX = 1;
            public static final int D4_INDEX = 2;
            public static final int D_4_INDEX = 3;
            public static final int E4_INDEX = 4;
            public static final int F4_INDEX = 5;
            public static final int F_4_INDEX = 6;
            public static final int G4_INDEX = 7;
            public static final int G_4_INDEX = 8;
            public static final int A4_INDEX = 9;
            public static final int A_4_INDEX = 10;
            public static final int B4_INDEX = 11;

            //Octave 5
            public static final int C5_INDEX = 12;
            public static final int C_5_INDEX = 13;
            public static final int D5_INDEX = 14;
            public static final int D_5_INDEX = 15;
            public static final int E5_INDEX = 16;
            public static final int F5_INDEX = 17;
            public static final int F_5_INDEX = 18;
            public static final int G5_INDEX = 19;
            public static final int G_5_INDEX = 20;
            public static final int A5_INDEX = 21;
            public static final int A_5_INDEX = 22;
            public static final int B5_INDEX = 23;

        // Sounds Map
        public static final Map<String, Integer> SOUNDS_MAP = Map.ofEntries(
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
                entry(G_5, G_5_INDEX),
                entry(A5, A5_INDEX),
                entry(A_5, A_5_INDEX),
                entry(B5, B5_INDEX)
        );

    // Row Map
    public static final Map<Integer, String> ROW_MAP = Map.ofEntries(
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
            entry(NUM_ROWS - 1 - G_5_INDEX, G_5),
            entry(NUM_ROWS - 1 - A5_INDEX, A5),
            entry(NUM_ROWS - 1 - A_5_INDEX, A_5),
            entry(NUM_ROWS - 1 - B5_INDEX, B5)
    );
}
