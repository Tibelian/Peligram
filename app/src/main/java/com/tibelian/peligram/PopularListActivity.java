package com.tibelian.peligram;

import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

public class PopularListActivity extends SingleFragmentActivity {

    private ImageView mGoBack;

    @Override
    protected Fragment createFragment() {
        return new PopularListFragment();
    }

    @Override
    protected void generateActionBar(ActionBar mActionBar) {

        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.toolbar_title);

        // access toolbar layout to set an event on
        // the chevron left icon to finish this activity
        View actionBarView = mActionBar.getCustomView();
        mGoBack = actionBarView.findViewById(R.id.toolbar_back);
        mGoBack.setVisibility(View.VISIBLE);
        mGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
