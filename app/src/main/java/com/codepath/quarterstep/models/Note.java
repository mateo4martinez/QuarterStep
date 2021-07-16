package com.codepath.quarterstep.models;

import android.widget.RelativeLayout;

public class Note {
    private String noteName;
    private RelativeLayout rlNote;

    public Note(String noteName, RelativeLayout rlNote) {
        this.noteName = noteName;
        this.rlNote = rlNote;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public String getNoteName() {
        return this.noteName;
    }

    public void setLayout(RelativeLayout rlNote) {
        this.rlNote = rlNote;
    }

    public RelativeLayout getLayout() {
        return this.rlNote;
    }
}
