package com.codepath.quarterstep.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

import com.codepath.quarterstep.models.Note;
import com.codepath.quarterstep.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class ArrangementView extends GridView {
    private List<List<Note>> grid;

    public ArrangementView(Context context) {
        super(context);
    }

    public ArrangementView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ArrangementView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void generateGrid() {
        this.grid = new ArrayList<>();
        for (int i = 0; i < Constants.NUM_ROWS; i++) {
            List<Note> row = new ArrayList<>();
            Note nameBlock = new Note();
            nameBlock.setRow(i);
            nameBlock.setCol(0);
            nameBlock.setNoteName(Constants.ROW_MAP.get(i));
            row.add(nameBlock);
            for (int j = 1; j < Constants.NUM_COLS; j++) {
                Note note = new Note();
                note.setRow(i);
                note.setCol(j);
                note.setNoteName(Constants.ROW_MAP.get(i));

                row.add(note);
            }
            this.grid.add(row);
        }
    }

    public List<List<Note>> getGrid() {
        return this.grid;
    }

    public List<Note> getRow(int position) {
        return this.grid.get(position);
    }

    public List<Note> getCol(int position) {
        List<Note> col = new ArrayList<>();
        for (int i = 0; i < Constants.NUM_ROWS; i++) {
            col.add(this.grid.get(i).get(position));
        }
        return col;
    }

    public void setNote(int row, int col, Note note) {
        this.grid.get(row).set(col, note);
    }

    public void deleteNote(int row, int col) {
        this.grid.get(row).set(col, new Note());
    }

    public View getViewByPosition(int position) {
        int firstPosition = this.getFirstVisiblePosition();
        int lastPosition = this.getLastVisiblePosition();

        if ((position < firstPosition) || (position > lastPosition))
            return null;

        return this.getChildAt(position - firstPosition);
    }
}
