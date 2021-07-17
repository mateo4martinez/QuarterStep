package com.codepath.quarterstep.utils;

import com.codepath.quarterstep.R;
import com.codepath.quarterstep.models.NameBlock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {
    // Note Names
        // Octave 4
        public static final String C4 = "C4";
        public static final String C_4 = "C_4";
        public static final String D4 = "D4";
        public static final String D_4 = "D_4";
        public static final String E4 = "E4";
        public static final String F4 = "F4";
        public static final String F_4 = "F_4";
        public static final String G4 = "G4";
        public static final String G_4 = "G_4";
        public static final String A4 = "A4";
        public static final String A_4 = "A_4";
        public static final String B4 = "B4";

        // Octave 5
        public static final String C5 = "C5";
        public static final String C_5 = "C_5";
        public static final String D5 = "D5";
        public static final String D_5 = "D_5";
        public static final String E5 = "E5";
        public static final String F5 = "F5";
        public static final String F_5 = "F_5";
        public static final String G5 = "G5";
        public static final String G_5 = "G_5";
        public static final String A5 = "A5";
        public static final String A_5 = "A_5";
        public static final String B5 = "B5";

    // Name Blocks
        // Octave 4
        public static final NameBlock C4_BLOCK = new NameBlock(C4);
        public static final NameBlock C_4_BLOCK = new NameBlock(C_4);
        public static final NameBlock D4_BLOCK = new NameBlock(D4);
        public static final NameBlock D_4_BLOCK = new NameBlock(D_4);
        public static final NameBlock E4_BLOCK = new NameBlock(E4);
        public static final NameBlock F4_BLOCK = new NameBlock(F4);
        public static final NameBlock F_4_BLOCK = new NameBlock(F_4);
        public static final NameBlock G4_BLOCK = new NameBlock(G4);
        public static final NameBlock G_4_BLOCK = new NameBlock(G_4);
        public static final NameBlock A4_BLOCK = new NameBlock(A4);
        public static final NameBlock A_4_BLOCK = new NameBlock(A_4);
        public static final NameBlock B4_BLOCK = new NameBlock(B4);

        // Octave 5
        public static final NameBlock C5_BLOCK = new NameBlock(C5);
        public static final NameBlock C_5_BLOCK = new NameBlock(C_5);
        public static final NameBlock D5_BLOCK = new NameBlock(D5);
        public static final NameBlock D_5_BLOCK = new NameBlock(D_5);
        public static final NameBlock E5_BLOCK = new NameBlock(E5);
        public static final NameBlock F5_BLOCK = new NameBlock(F5);
        public static final NameBlock F_5_BLOCK = new NameBlock(F_5);
        public static final NameBlock G5_BLOCK = new NameBlock(G5);
        public static final NameBlock G_5_BLOCK = new NameBlock(G_5);
        public static final NameBlock A5_BLOCK = new NameBlock(A5);
        public static final NameBlock A_5_BLOCK = new NameBlock(A_5);
        public static final NameBlock B5_BLOCK = new NameBlock(B5);

    // Name Blocks Array
        public static final List<NameBlock> NAME_BLOCKS = Arrays.asList(
                C4_BLOCK, C_4_BLOCK, D4_BLOCK, D_4_BLOCK, E4_BLOCK, F4_BLOCK, F_4_BLOCK, G4_BLOCK,
                G_4_BLOCK, A4_BLOCK, A_4_BLOCK, B4_BLOCK,
                C5_BLOCK, C_5_BLOCK, D5_BLOCK, D_5_BLOCK, E5_BLOCK, F5_BLOCK, F_5_BLOCK, G5_BLOCK,
                G_5_BLOCK, A5_BLOCK, A_5_BLOCK, B5_BLOCK
    );

    // Sounds
        public static final int[] SOUNDS_ARRAY = new int[]{
            R.raw.c4, R.raw.c_4, R.raw.d4, R.raw.d_4, R.raw.e4, R.raw.f4, R.raw.f_4, R.raw.g4,
            R.raw.g_4, R.raw.a4, R.raw.a_4, R.raw.b4,
            R.raw.c5, R.raw.c_5, R.raw.d5, R.raw.d_5, R.raw.e5, R.raw.f5, R.raw.f_5, R.raw.g5,
            R.raw.g_5, R.raw.a5, R.raw.a_5, R.raw.b5
    };
}
