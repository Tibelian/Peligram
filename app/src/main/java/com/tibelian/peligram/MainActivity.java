package com.tibelian.peligram;

import androidx.fragment.app.Fragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new PostListFragment();
    }

}