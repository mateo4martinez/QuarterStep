package com.codepath.quarterstep.models;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.codepath.quarterstep.utils.Constants;

import java.util.List;

public class Note {
    private String noteName;
    private RelativeLayout rlCell;
    private boolean playable;
    private boolean isNameBlock;
    private int position; // adapter position
    private int row;
    private int col;

    public Note() {
        this.noteName = null;
        this.rlCell = null;
        this.playable = false;
        this.isNameBlock = false;
        this.position = 0;
        this.row = 0;
        this.col = 0;
    }

    public Note(String noteName, RelativeLayout rlCell) {
        this.noteName = noteName;
        this.rlCell = rlCell;
        this.playable = false;
        this.isNameBlock = false;
        this.position = 0;
        this.row = 0;
        this.col = 0;
    }

    public void triggerFlag() {
        this.isNameBlock = true;
    }

    public boolean getFlag() {
        return this.isNameBlock;
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

    public boolean isPlayable() {
        return this.playable;
    }

    public void setAdapterPosition(int position) {
        this.position = position;
    }

    public int getAdapterPosition() {
        return this.position;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getRow() {
        return this.row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getCol() {
        return this.col;
    }

    public void selected(Context context) {
        if (!this.playable) {
            this.rlCell.setBackgroundColor(ContextCompat.getColor(context, Constants.COLOR_MAP.get(this.noteName)));
            this.playable = true;
        }
        else {
            this.rlCell.setBackgroundColor(ContextCompat.getColor(context, Constants.COLOR_MAP.get(Constants.NOTE_INACTIVE)));
            this.playable = false;
        }
    }
}
