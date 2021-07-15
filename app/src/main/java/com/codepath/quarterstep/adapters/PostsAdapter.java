package com.codepath.quarterstep.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.quarterstep.R;
import com.codepath.quarterstep.models.Post;

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
        private String timeAgo;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ibPlay = itemView.findViewById(R.id.ibPlay);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
            tvCaption = itemView.findViewById(R.id.tvCaption);
            tvCharacteristics = itemView.findViewById(R.id.tvCharacteristics);
        }

        public void bind(Post post) {
            tvUsername.setText("@" + post.getUser().getUsername());
            tvCaption.setText(post.getCaption());
            tvCharacteristics.setText(post.getCharacteristics());

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
