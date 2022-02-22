package com.tibelian.peligram;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tibelian.peligram.model.Post;
import com.tibelian.peligram.model.PostLab;

import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder> {

    private List<Post> mPosts;
    private Context mContext;

    public PostListAdapter(List<Post> posts, Context context) {
        mPosts = posts;
        mContext = context;
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
        private ImageView mAddLike;

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
            if (post.getImage() != -1)
                mImage.setImageResource(post.getImage());
            mLikes.setText("" + post.getLikes());

            // on click like button
            mAddLike.setOnClickListener(v -> {

                // increase number of likes
                post.setLikes(post.getLikes() + 1);

                // update the text view
                mLikes.setText("" + post.getLikes());

                // and finally save changes into database
                PostLab.get(mContext).updatePost(post);

                // scale animation to the like button
                scaleThis(mAddLike);

            });
        }

        private void scaleThis(View view)
        {
            // animation type
            ScaleAnimation increase  = new ScaleAnimation(1, 1.2f, 1, 1.2f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            ScaleAnimation decrease = new ScaleAnimation(1.2f, 1, 1.2f, 1,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            // animation duration
            increase.setDuration(500);
            decrease.setDuration(500);
            // animation persist changes
            increase.setFillAfter(true);
            decrease.setFillAfter(true);
            // apply animation
            view.startAnimation(increase);
            view.startAnimation(decrease);
        }

    }

}
