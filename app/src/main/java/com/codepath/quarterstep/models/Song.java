package com.codepath.quarterstep.models;

import android.content.Context;

import com.codepath.quarterstep.utils.Constants;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("Song")
public class Song extends ParseObject {
    public static final String KEY_SONG = "songString";
    public static final String KEY_USER = "user";
    public static final String KEY_NAME = "name";

    private List<List<Note>> rawSong;
    private List<List<Note>> chords;
    private String parseString;
    private boolean favorite;
    private boolean saved;

    public Song() {
        this.rawSong = new ArrayList<>();
        this.chords = new ArrayList<>();
        this.parseString = "";
        this.favorite = false;
        this.saved = false;
    }

    public Song(List<List<Note>> rawSong) {
        this.rawSong = rawSong;
        this.chords = extractChords();
        this.parseString = "";
        this.favorite = false;
        this.saved = false;
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public String getSong() {
        return getString(KEY_SONG);
    }

    public void setSong(String song) {
        put(KEY_SONG, song);
    }

    public String getName() {
        return getString(KEY_NAME);
    }

    public void setName(String name) {
        put(KEY_NAME, name);
    }

    public String convertToParseString() {
        for (List<Note> row: this.rawSong) {
            for (Note note: row) {
                if (!note.getFlag() && note.isPlayable()) {
                    this.parseString += "1";
                } else {
                    this.parseString += "0";
                }
            }
        }
        return this.parseString;
    }

    public static List<List<Note>> convertToRawSong(String raw) {
        List<List<Note>> rawSong = new ArrayList<>();
        int multiplier = Constants.NUM_COLS;

        for (int i = 0; i < Constants.NUM_ROWS; i++) {
            List<Note> row = new ArrayList<>();
            Note nameblock = new Note();
            nameblock.setRow(i);
            nameblock.setCol(0);
            nameblock.setNoteName(Constants.ROW_MAP.get(i));
            nameblock.triggerFlag();
            row.add(nameblock);
            for (int j = 1; j < Constants.NUM_COLS; j++) {
                Note note = new Note();
                note.setRow(i);
                note.setCol(j);
                note.setNoteName(Constants.ROW_MAP.get(i));
                if (raw.charAt((i * multiplier) + j) == '1') {
                    note.makePlayable();
                }
                row.add(note);
            }
            rawSong.add(row);
        }
        return rawSong;
    }

    public boolean isSaved() {
        return this.saved;
    }

    public void actionSave() {
        this.saved = !this.saved;
    }

    public boolean isFavorite() {
        return this.favorite;
    }

    public void actionFavorite() {
        this.favorite = !this.favorite;
    }

    public List<List<Note>> getChords() {
        return this.chords;
    }

    public void addChord(List<Note> rawChord) {
        List<Note> chord = extractOneChord(rawChord);
        this.chords.add(chord);
    }

    private List<List<Note>> extractChords() {
        // Separate grid into raw columns
        List<List<Note>> rawChords = separateColumns();

        // Get playable notes from raw columns, creating a chord (group of notes played at same time)
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

    private List<List<Note>> separateColumns() {
        List<List<Note>> rawChords = new ArrayList<>();
        for (int i = 1; i < Constants.NUM_COLS; i++) {
            rawChords.add(separateOneColumn(i));
        }
        return rawChords;
    }

    private List<Note> separateOneColumn(int position) {
        List<Note> rawChord = new ArrayList<>();
        for (int i = 0; i < Constants.NUM_ROWS; i++) {
            rawChord.add(this.rawSong.get(i).get(position));
        }
        return rawChord;
    }
}
