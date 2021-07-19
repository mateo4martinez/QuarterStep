package com.codepath.quarterstep.utils;

import android.content.Context;
import android.media.SoundPool;

import com.codepath.quarterstep.R;

public class MySoundPool extends SoundPool {
    private int[] sounds;
    private boolean loaded;

    public MySoundPool(int maxStreams, int streamType, int srcQuality) {
        super(maxStreams, streamType, srcQuality);
        this.loaded = false;
    }

    public void loadSounds(Context context, int[] sounds) {
        this.sounds = new int[Constants.NUM_NOTES];
        for (int i = 0; i < sounds.length; i++) {
            this.sounds[i] = super.load(context, sounds[i], 1);
        }

        this.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });
    }

    public boolean isLoaded() {
        return this.loaded;
    }

    public int[] getSounds() {
        return this.sounds;
    }
}
