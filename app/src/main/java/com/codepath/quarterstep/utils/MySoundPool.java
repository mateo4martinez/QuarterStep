package com.codepath.quarterstep.utils;

import android.content.Context;
import android.media.SoundPool;

import com.codepath.quarterstep.R;

public class MySoundPool extends SoundPool {
    private int[] sounds;

    public MySoundPool(int maxStreams, int streamType, int srcQuality) {
        super(maxStreams, streamType, srcQuality);
    }

    // Move to arrangement fragment when ready
    public void loadSounds(Context context, int[] sounds) {
        this.sounds = new int[24];
        for (int i = 0; i < sounds.length; i++) {
            this.sounds[i] = super.load(context, sounds[i], 1);
        }
    }
}
