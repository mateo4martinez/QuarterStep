package com.codepath.quarterstep.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.quarterstep.R;
import com.codepath.quarterstep.activities.ShareActivity;
import com.codepath.quarterstep.models.Note;
import com.codepath.quarterstep.models.Song;
import com.codepath.quarterstep.models.SongReference;
import com.codepath.quarterstep.utils.Constants;
import com.codepath.quarterstep.utils.DoubleClickListener;
import com.codepath.quarterstep.utils.SongPlayer;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

public class SavesAdapter extends BaseAdapter {
    public static final String TAG = "SavesAdapter";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Context context;
    private List<SongReference> songs;
    private List<DocumentSnapshot> docs;

    public SavesAdapter(Context context, List<SongReference> songs, List<DocumentSnapshot> docs) {
        this.context = context;
        this.songs = songs;
        this.docs = docs;
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DocumentSnapshot doc = docs.get(position);
        SongReference reference = songs.get(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_save, parent, false);
        }

        ImageButton ibPlay = convertView.findViewById(R.id.ibPlay);
        ImageView ivFavorite = convertView.findViewById(R.id.ivFavorite);
        TextView tvSongName = convertView.findViewById(R.id.tvSongName);
        TextView tvCreatedAt = convertView.findViewById(R.id.tvCreatedAt);

        tvSongName.setText(reference.getName());
        tvCreatedAt.setText(reference.getCreatedAt());

        if (reference.isFavorite()) {
            Glide.with(context).load(R.drawable.favorite_filled).into(ivFavorite);
        } else {
            Glide.with(context).load(R.drawable.favorite_border).into(ivFavorite);
        }

        // Create song object
        String songString = reference.getSongString();
        List<List<Note>> rawSong = Song.convertToRawSong(songString);
        Song song = new Song(rawSong);
        SongPlayer songPlayer = new SongPlayer(context, Constants.SOUNDPOOL, song);

        ibPlay.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(context, ShareActivity.class);
                intent.putExtra(Constants.NAME_KEY, reference.getName());
                intent.putExtra(Constants.SONG_KEY, reference.getSongString());
                intent.putExtra(Constants.SAVED_KEY, true);
                intent.putExtra(Constants.FAVORITE_KEY, reference.isFavorite());
                intent.putExtra(Constants.DOC_ID_KEY, doc.getId());

                context.startActivity(intent);
                return true;
            }
        });

        ibPlay.setOnClickListener(new DoubleClickListener() {
            @Override
            public void onDoubleClick() {
                reference.actionFavorite();
                if (reference.isFavorite()) {
                    Glide.with(context).load(R.drawable.favorite_filled).into(ivFavorite);
                    db.collection("songs").document(doc.getId()).update(Map.of("favorite", true));
                } else {
                    Glide.with(context).load(R.drawable.favorite_border).into(ivFavorite);
                    db.collection("songs").document(doc.getId()).update(Map.of("favorite", false));
                }
                notifyDataSetChanged();
            }

            @Override
            public void onSingleClick() {
                YoYo.with(Techniques.Pulse).duration(Constants.NOTE_DELAY * 2).repeat(6).playOn(ibPlay);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            songPlayer.playSong();
                        } catch (InterruptedException e) {
                            Log.e(TAG, "Issue with playing song from save layout.", e);
                        }
                    }
                }).start();
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(context, ShareActivity.class);
                intent.putExtra(Constants.NAME_KEY, reference.getName());
                intent.putExtra(Constants.SONG_KEY, reference.getSongString());
                intent.putExtra(Constants.SAVED_KEY, true);
                intent.putExtra(Constants.FAVORITE_KEY, reference.isFavorite());

                context.startActivity(intent);
                return true;
            }
        });

        return convertView;
    }

    public void clear() {
        songs.clear();
        docs.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<SongReference> list1, List<DocumentSnapshot> list2) {
        songs.addAll(list1);
        docs.addAll(list2);
        notifyDataSetChanged();
    }
}
