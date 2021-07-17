package com.codepath.quarterstep.models;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class Song {
    private Context context;
    private List<Note> notes;

    public Song(Context context) {
        this.context = context;
        this.notes = new ArrayList<>();
    }

    public Song(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public List<Note> getNotes() {
        return notes;
    }
}
