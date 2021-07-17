package com.codepath.quarterstep.utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.codepath.quarterstep.R;
import com.codepath.quarterstep.models.Note;
import com.codepath.quarterstep.models.Song;

import java.util.List;

public class SongPlayer {
    private Context context;
    private MediaPlayer mediaPlayer;

    public SongPlayer(Context context) {
        this.context = context;
    }

    public void createNote(Note note) {
        String noteName = note.getNoteName();
        switch (noteName.charAt(0)) {
            case 'C':
                switch (noteName.charAt(1)) {
                    case '_':
                        switch (noteName.charAt(2)) {
                            case '4':
                                mediaPlayer = MediaPlayer.create(context, R.raw.c_4);
                            case '5':
                                mediaPlayer = MediaPlayer.create(context, R.raw.c_5);
                        }
                    default:
                        switch (noteName.charAt(2)) {
                            case '4':
                                mediaPlayer = MediaPlayer.create(context, R.raw.c4);
                            case '5':
                                mediaPlayer = MediaPlayer.create(context, R.raw.c5);
                        }
                }
            case 'D':
                switch (noteName.charAt(1)) {
                    case '_':
                        switch (noteName.charAt(2)) {
                            case '4':
                                mediaPlayer = MediaPlayer.create(context, R.raw.d_4);
                            case '5':
                                mediaPlayer = MediaPlayer.create(context, R.raw.d_5);
                        }
                    default:
                        switch (noteName.charAt(2)) {
                            case '4':
                                mediaPlayer = MediaPlayer.create(context, R.raw.d4);
                            case '5':
                                mediaPlayer = MediaPlayer.create(context, R.raw.d5);
                        }
                }
            case 'E':
                switch (noteName.charAt(1)) {
                    case '4':
                        mediaPlayer = MediaPlayer.create(context, R.raw.e4);
                    case '5':
                        mediaPlayer = MediaPlayer.create(context, R.raw.e5);
                }
            case 'F':
                switch (noteName.charAt(1)) {
                    case '_':
                        switch (noteName.charAt(2)) {
                            case '4':
                                mediaPlayer = MediaPlayer.create(context, R.raw.f_4);
                            case '5':
                                mediaPlayer = MediaPlayer.create(context, R.raw.f_5);
                        }
                    default:
                        switch (noteName.charAt(2)) {
                            case '4':
                                mediaPlayer = MediaPlayer.create(context, R.raw.f4);
                            case '5':
                                mediaPlayer = MediaPlayer.create(context, R.raw.f5);
                        }
                }
            case 'G':
                switch (noteName.charAt(1)) {
                    case '_':
                        switch (noteName.charAt(2)) {
                            case '4':
                                mediaPlayer = MediaPlayer.create(context, R.raw.g_4);
                            case '5':
                                mediaPlayer = MediaPlayer.create(context, R.raw.g_5);
                        }
                    default:
                        switch (noteName.charAt(2)) {
                            case '4':
                                mediaPlayer = MediaPlayer.create(context, R.raw.g4);
                            case '5':
                                mediaPlayer = MediaPlayer.create(context, R.raw.g_5);
                        }
                }
            case 'A':
                switch (noteName.charAt(1)) {
                    case '_':
                        switch (noteName.charAt(2)) {
                            case '4':
                                mediaPlayer = MediaPlayer.create(context, R.raw.a_4);
                            case '5':
                                mediaPlayer = MediaPlayer.create(context, R.raw.a_5);
                        }
                    default:
                        switch (noteName.charAt(2)) {
                            case '4':
                                mediaPlayer = MediaPlayer.create(context, R.raw.a4);
                            case '5':
                                mediaPlayer = MediaPlayer.create(context, R.raw.a5);
                        }
                }
            case 'B':
                switch (noteName.charAt(1)) {
                    case '4':
                        mediaPlayer = MediaPlayer.create(context, R.raw.b4);
                    case '5':
                        mediaPlayer = MediaPlayer.create(context, R.raw.b5);
                }
        }
    }

    public void playNote() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }

        stopNote();
    }

    public void stopNote() {
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
            }
        });
    }

    public void playSong(Song song) {
        List<Note> notes = song.getNotes();
        for (Note note :notes) {
            createNote(note);
            playNote();
        }
    }
}