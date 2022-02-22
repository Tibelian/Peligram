package com.tibelian.peligram;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new PostListFragment();
    }

    @Override
    protected void generateActionBar(ActionBar mActionBar) {

        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.toolbar_title);

    }

}