package com.codepath.quarterstep.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.quarterstep.R;
import com.codepath.quarterstep.models.Note;
import com.codepath.quarterstep.models.Post;
import com.codepath.quarterstep.models.Song;
import com.codepath.quarterstep.utils.Constants;
import com.codepath.quarterstep.utils.SongPlayer;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    public static final String TAG = "PostsAdapter";

    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @NotNull
    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PostsAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageButton ibPlay;
        private TextView tvUsername;
        private TextView tvCreatedAt;
        private TextView tvCaption;
        private TextView tvCharacteristics;
        private TextView tvSongName;
        private String songString;
        private SongPlayer songPlayer;
        private Song song;
        private String timeAgo;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ibPlay = itemView.findViewById(R.id.ibPlay);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
            tvCaption = itemView.findViewById(R.id.tvCaption);
            tvCharacteristics = itemView.findViewById(R.id.tvCharacteristics);
            tvSongName = itemView.findViewById(R.id.tvSongName);
        }

        public void bind(Post post) {
            tvUsername.setText("@" + post.getUser().getUsername());
            tvCaption.setText(post.getCaption());
            tvCharacteristics.setText(post.getCharacteristics());
            tvSongName.setText(post.getName());

            // Parse string into Song object
            songString = post.getSongString();
            List<List<Note>> rawSong = Song.convertToRawSong(songString);
            song = new Song(rawSong);
            songPlayer = new SongPlayer(context, Constants.SOUNDPOOL, song);

            ibPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    YoYo.with(Techniques.Pulse).duration(Constants.NOTE_DELAY * 2).repeat(8).playOn(ibPlay);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                songPlayer.playSong();
                            } catch (InterruptedException e) {
                                Log.e(TAG, "Issue with playing song from post.", e);
                            }
                        }
                    }).start();
                }
            });

            Date createdAt = post.getCreatedAt();
            timeAgo = Post.calculateTimeAgo(createdAt);
            tvCreatedAt.setText(timeAgo);
        }
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }
}
