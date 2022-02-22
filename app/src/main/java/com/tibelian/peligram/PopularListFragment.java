package com.tibelian.peligram;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tibelian.peligram.model.Post;
import com.tibelian.peligram.model.PostLab;

import java.util.List;

public class PopularListFragment extends Fragment {

    private RecyclerView mPostRecyclerView;
    private PostListAdapter mPostListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular_list, container, false);

        // loads the adapter with the posts from the laboratory
        List<Post> posts = PostLab.get(getActivity()).getPopularPosts(5);
        mPostListAdapter = new PostListAdapter(posts, getActivity());

        // create the list using the prev adapter
        mPostRecyclerView = view.findViewById(R.id.popular_recycler);
        mPostRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPostRecyclerView.setAdapter(mPostListAdapter);

        // now this view will be rendered
        return view;
    }

}