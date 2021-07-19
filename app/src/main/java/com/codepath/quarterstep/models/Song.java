package com.codepath.quarterstep.models;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class Song {
    private Context context;
    private List<List<Note>> chords;

    public Song(Context context) {
        this.context = context;
        this.chords = new ArrayList<>();
    }

    public Song(Context context, List<List<Note>> rawChords) {
        this.context = context;
        this.chords = extractChords(rawChords);
    }

    public List<List<Note>> getChords() {
        return this.chords;
    }

    public void addChord(List<Note> rawChord) {
        this.chords.add(extractOneChord(rawChord));
    }

    private List<Note> extractOneChord(List<Note> notes) {
        List<Note> chord = new ArrayList<>();
        for (Note note: notes) {
            if (note.getNoteName() != null) {
                chord.add(note);
            }
        }
        return chord;
    }

    private List<List<Note>> extractChords(List<List<Note>> rawChords) {
        List<List<Note>> extractedChords = new ArrayList<>();
        for (List<Note> rawChord: rawChords) {
            extractedChords.add(extractOneChord(rawChord));
        }
        return extractedChords;
    }
}
