package com.codepath.quarterstep.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;

import com.codepath.quarterstep.R;
import com.codepath.quarterstep.models.Note;
import com.codepath.quarterstep.models.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class SongPlayer {
    public static final String TAG = "SongPlayer";

    private Context context;
    private SoundPool soundPool;
    private Song song;
    private boolean loaded;

    public SongPlayer(Context context, SoundPool soundPool) {
        this.context = context;
        this.soundPool = soundPool;
        this.song = null;
        this.loaded = false;
    }

    public SongPlayer(Context context, SoundPool soundPool, Song song) {
        this.context = context;
        this.soundPool = soundPool;
        this.song = song;
        this.loaded = false;
    }

    public void addSong(Song song) {
        this.song = song;
    }

    public Song getSong() {
        return this.song;
    }

    public boolean isLoaded() {
        return this.loaded;
    }

    public void playSong() throws InterruptedException {
        List<List<Note>> chords = this.song.getChords();
        List<Thread> threads = createChordThreads(chords);

        // Begin playing song
        for (Thread thread: threads) {
            thread.start();
            thread.join(); // wait for thread to finish
            TimeUnit.MILLISECONDS.sleep(Constants.NOTE_DELAY);
        }
    }

    private List<Thread> createChordThreads(List<List<Note>> chords) {
        List<Thread> threads = new ArrayList<>();
        for (List<Note> chord: chords) {
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        playChord(chord);
                    } catch (BrokenBarrierException e) {
                        Log.e(TAG, "Issue with broken barrier", e);
                    } catch (InterruptedException e) {
                        Log.e(TAG, "Issue with interrupted thread", e);
                    }
                }
            }));
        }
        return threads;
    }

    private void playChord(List<Note> chord) throws BrokenBarrierException, InterruptedException {
        final CyclicBarrier gate = new CyclicBarrier(chord.size() + 1);

        List<Thread> threads = createNoteThreads(gate, chord);
        for (Thread thread: threads) {
            thread.start();
        }

        // release all barriers
        gate.await();
    }

    private List<Thread> createNoteThreads(CyclicBarrier gate, List<Note> chord) {
        List<Thread> threads = new ArrayList<>();
        for (Note note: chord) {
            String noteName = note.getNoteName();

            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        gate.await(); // barrier: blocks threads from playing note
                        playOneNote(noteName);
                    } catch (BrokenBarrierException e) {
                        Log.e(TAG, "Issue with broken barrier", e);
                    } catch (InterruptedException e) {
                        Log.e(TAG, "Issue with interrupted thread", e);
                    }
                }
            }));
        }
        return threads;
    }

    public void playOneNote(String noteName) {
        int index = Constants.SOUNDS_MAP.get(noteName);
        soundPool.play(Constants.SOUNDS[index], 1, 1, 0, 0, 1);
    }
}
