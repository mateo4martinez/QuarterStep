package com.codepath.quarterstep.models;

import android.content.Context;

import com.codepath.quarterstep.utils.Constants;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class Song {
    private Context context;
    private List<List<Note>> rawSong;
    private List<List<Note>> chords;
    private ParseUser user;
    private String songName;
    private boolean favorite;

    public Song(Context context, ParseUser user) {
        this.context = context;
        this.user = user;
        this.rawSong = new ArrayList<>();
        this.chords = new ArrayList<>();
        this.songName = "";
        this.favorite = false;
    }

    public Song(Context context, List<List<Note>> rawSong, ParseUser user) {
        this.context = context;
        this.user = user;
        this.rawSong = rawSong;
        this.chords = extractChords(rawSong);
        this.songName = "";
        this.favorite = false;
    }

    public void setSongName(String name) {
        this.songName = name;
    }

    public String getSongName() {
        return this.songName;
    }

    public ParseUser getUser() {
        return this.user;
    }

    public boolean isFavorite() {
        return this.favorite;
    }

    public boolean actionFavorite() {
        this.favorite = !this.favorite;
        return this.favorite;
    }

    public List<List<Note>> getChords() {
        return this.chords;
    }

    public void addChord(List<Note> rawChord) {
        List<Note> chord = extractOneChord(rawChord);
        this.chords.add(chord);
    }

    private List<List<Note>> extractChords(List<List<Note>> rawSong) {
        List<List<Note>> rawChords = convertToChords(rawSong);

        List<List<Note>> extractedChords = new ArrayList<>();
        for (List<Note> rawChord: rawChords) {
            extractedChords.add(extractOneChord(rawChord));
        }
        return extractedChords;
    }

    private List<Note> extractOneChord(List<Note> notes) {
        List<Note> chord = new ArrayList<>();
        for (Note note: notes) {
            if (note.isPlayable()) {
                chord.add(note);
            }
        }
        return chord;
    }

    private List<List<Note>> convertToChords(List<List<Note>> rawSong) {
        List<List<Note>> rawChords = new ArrayList<>();
        for (int i = 0; i < Constants.NUM_COLS; i++) {
            rawChords.add(convertOneChord(i));
        }
        return rawChords;
    }

    private List<Note> convertOneChord(int position) {
        List<Note> rawChord = new ArrayList<>();
        for (int i = 0; i < Constants.NUM_ROWS; i++) {
            rawChord.add(this.rawSong.get(i).get(position));
        }
        return rawChord;
    }
}
