package com.tibelian.peligram;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    // force dynamic fragment creator
    protected abstract Fragment createFragment();

    // force to generate a toolbar
    protected abstract void generateActionBar(ActionBar mActionBar);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // manage action bar and his default options
        generateActionBar(getSupportActionBar());

        // parent onCreate
        super.onCreate(savedInstanceState);

        // set target xml layout
        setContentView(R.layout.activity_main);

        // create the fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_main);
        if(fragment == null){
            fragment = createFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_main, fragment)
                    .commit();
        }

    }

}
