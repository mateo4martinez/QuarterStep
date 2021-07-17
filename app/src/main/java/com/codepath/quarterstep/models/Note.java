package com.codepath.quarterstep.models;

import android.widget.RelativeLayout;
import android.widget.TextView;

public class Note {
    private String noteName;
    private RelativeLayout rlCell;

    public Note() {
        this.noteName = null;
        this.rlCell = null;
    }

    public Note(String noteName, RelativeLayout rlCell) {
        this.noteName = noteName;
        this.rlCell = rlCell;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public String getNoteName() {
        return this.noteName;
    }

    public void setLayout(RelativeLayout rlCell) {
        this.rlCell = rlCell;
    }

    public RelativeLayout getLayout() {
        return this.rlCell;
    }
}
