package com.codepath.quarterstep.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.quarterstep.R;
import com.codepath.quarterstep.activities.EndlessRecyclerViewScrollListener;
import com.codepath.quarterstep.adapters.PostsAdapter;
import com.codepath.quarterstep.models.Post;
import com.codepath.quarterstep.utils.ScreenSlidePageFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FeedFragment extends ScreenSlidePageFragment {
    public static final String TAG = "FeedFragment";

    private RecyclerView rvPosts;
    private SwipeRefreshLayout swipeContainer;
    private EndlessRecyclerViewScrollListener scrollListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    protected PostsAdapter adapter;
    protected List<Post> allPosts;

    private int limit = 0;
    private boolean loadMore = false;

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvPosts = view.findViewById(R.id.rvPosts);
        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(getContext(), allPosts);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(linearLayoutManager);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                //queryMorePosts();
            }
        };

        setupRefresh(view);

        queryPostsFirebase();
    }

    private void queryPostsFirebase() {
        Task<QuerySnapshot> queryTask;
        if (loadMore) {
            queryTask = db.collection("posts")
                    .orderBy("createdAt", Query.Direction.DESCENDING)
                    .limit(10)
                    .startAfter(limit).get();
        } else {
            queryTask = db.collection("posts")
                    .orderBy("createdAt", Query.Direction.DESCENDING)
                    .limit(20).get();
        }

        queryTask.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> documents = task.getResult().getDocuments();
                    List<Post> lst = new ArrayList<>();
                    for (DocumentSnapshot doc: documents) {
                        lst.add(doc.toObject(Post.class));
                    }

                    limit += lst.size();
                    if (lst.isEmpty()) {
                        loadMore = false;
                    } else {
                        loadMore = true;
                    }

                    allPosts.addAll(lst);
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, "Issue with querying posts.", task.getException());
                }
            }
        });
    }

    private void setupRefresh(View view) {
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                queryPostsFirebase();
                adapter.addAll(allPosts);
                swipeContainer.setRefreshing(false);
                scrollListener.resetState();
            }
        });
    }
}