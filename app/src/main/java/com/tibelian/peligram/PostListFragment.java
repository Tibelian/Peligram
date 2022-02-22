package com.tibelian.peligram;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tibelian.peligram.model.Post;
import com.tibelian.peligram.model.PostLab;

import java.io.IOException;
import java.util.List;

public class PostListFragment extends Fragment {

    private RecyclerView mPostRecyclerView;
    private PostListAdapter mPostListAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // add custom menu options
        setHasOptionsMenu(true);
    }

    // declare the menu's layout
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.options_post_list, menu);
    }

    // event on click menu option
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_best:
                startActivity(new Intent(getActivity(), PopularListActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_list, container, false);

        // loads the adapter with the posts from the laboratory
        List<Post> posts = PostLab.get(getActivity()).getPosts();
        mPostListAdapter = new PostListAdapter(posts, getActivity());

        // create the list using the prev adapter
        mPostRecyclerView = view.findViewById(R.id.post_recycler);
        mPostRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPostRecyclerView.setAdapter(mPostListAdapter);

        // now this view will be rendered
        return view;
    }

}