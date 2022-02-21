package com.tibelian.peligram;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tibelian.peligram.model.Post;
import com.tibelian.peligram.model.PostLab;

import java.util.List;

public class PostListFragment extends Fragment {

    private RecyclerView mPostRecyclerView;
    private PostListAdapter mPostListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_list, container, false);

        // loads the adapter with the posts from the laboratory
        List<Post> posts = PostLab.get(getActivity()).getPosts();
        mPostListAdapter = new PostListAdapter(posts);

        // create the list using the prev adapter
        mPostRecyclerView = view.findViewById(R.id.post_recycler);
        mPostRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPostRecyclerView.setAdapter(mPostListAdapter);

        // now this view will be rendered
        return view;
    }


    // my custom list adapter
    private class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder> {

        private List<Post> mPosts;

        public PostListAdapter(List<Post> posts) {
            mPosts = posts;
        }
        public void setPosts(List<Post> posts) {
            mPosts = posts;
        }
        public int getItemCount() {
            return mPosts.size();
        }

        public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_post, parent, false);
            return new PostViewHolder(view);
        }

        public void onBindViewHolder(PostViewHolder viewHolder, final int position) {
            viewHolder.bind(mPosts.get(position));
        }

        public class PostViewHolder extends RecyclerView.ViewHolder {

            private TextView mTitle;
            private ImageView mImage;
            private TextView mLikes;
            private Button mAddLike;

            public PostViewHolder(View view) {
                super(view);
                mTitle = view.findViewById(R.id.post_title);
                mImage = view.findViewById(R.id.post_image);
                mLikes = view.findViewById(R.id.post_likes);
                mAddLike = view.findViewById(R.id.post_add_like);
            }

            public void bind(Post post)
            {
                // show data
                mTitle.setText(post.getTitle());
                mImage.setImageResource(post.getImage());
                mLikes.setText((post.getLikes()));

                // on click like button
                mAddLike.setOnClickListener(v -> {

                    // increase number of likes
                    post.setLikes(post.getLikes() + 1);

                    // update the text view
                    mLikes.setText(post.getLikes());

                    // and finally save changes into database
                    PostLab.get(getActivity()).updatePost(post);

                });
            }

        }

    }
}