package com.codepath.quarterstep.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.quarterstep.R;
import com.codepath.quarterstep.activities.ShareActivity;
import com.codepath.quarterstep.models.Note;
import com.codepath.quarterstep.models.Song;
import com.codepath.quarterstep.models.SongReference;
import com.codepath.quarterstep.utils.Constants;
import com.codepath.quarterstep.utils.SongPlayer;

import java.util.List;

public class SavesAdapter extends BaseAdapter {
    public static final String TAG = "SavesAdapter";

    private Context context;
    private List<SongReference> songs;

    public SavesAdapter(Context context, List<SongReference> songs) {
        this.context = context;
        this.songs = songs;
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
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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

        // Create song object
        String songString = reference.getSongString();
        List<List<Note>> rawSong = Song.convertToRawSong(songString);
        Song song = new Song(rawSong);
        SongPlayer songPlayer = new SongPlayer(context, Constants.SOUNDPOOL, song);

        ibPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    songPlayer.playSong();
                } catch (InterruptedException e) {
                    Log.e(TAG, "Issue with playing song from save layout.", e);
                }
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(context, ShareActivity.class);
                intent.putExtra(Constants.NAME_KEY, reference.getName());
                intent.putExtra(Constants.SONG_KEY, reference.getSongString());
                intent.putExtra(Constants.SAVED_KEY, true);

                context.startActivity(intent);
                return true;
            }
        });

        return convertView;
    }

    public void clear() {
        songs.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<SongReference> list) {
        songs.addAll(list);
        notifyDataSetChanged();
    }
}
