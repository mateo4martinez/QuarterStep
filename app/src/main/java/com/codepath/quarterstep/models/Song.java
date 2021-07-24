package com.codepath.quarterstep.models;

import android.content.Context;

import com.codepath.quarterstep.utils.Constants;
import com.parse.ParseUser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Song {
    private Context context;
    private List<List<Note>> rawSong;
    private List<List<Note>> chords;
    private ParseUser user;
    private String songName;
    private String parseString;
    private boolean favorite;

    public Song(Context context, ParseUser user) {
        this.context = context;
        this.user = user;
        this.rawSong = new ArrayList<>();
        this.chords = new ArrayList<>();
        this.songName = "";
        this.parseString = "";
        this.favorite = false;
    }

    public Song(Context context, ParseUser user, List<List<Note>> rawSong) {
        this.context = context;
        this.user = user;
        this.rawSong = rawSong;
        this.chords = extractChords(rawSong);
        this.songName = "";
        this.parseString = "";
        this.favorite = false;
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
        int multiplier = Constants.NUM_ROWS;

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
        }

        return rawSong;
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
        List<List<Note>> rawChords = separateColumns(rawSong);

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

    private List<List<Note>> separateColumns(List<List<Note>> rawSong) {
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
