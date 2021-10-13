package com.example.novulis_dev_app_v01.activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.novulis_dev_app_v01.R;
import com.example.novulis_dev_app_v01.callbacks.FragmentCallback;
import com.example.novulis_dev_app_v01.fragments.ClubSelectionFragment;
import com.example.novulis_dev_app_v01.fragments.HomeFragment;
import com.example.novulis_dev_app_v01.fragments.LibraryFragment;
import com.example.novulis_dev_app_v01.fragments.LogFragment;
import com.example.novulis_dev_app_v01.fragments.ProfileFragment;
import com.example.novulis_dev_app_v01.fragments.SocialFragment;
import com.example.novulis_dev_app_v01.model.Profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationActivity extends AppCompatActivity implements FragmentCallback {

    // Nav bar variables
    BottomNavigationView bottomNavigationView;


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        // Copied Variables
        Context mContext = this;

        // Load the fragments and set listener for nav bar
        loadFragments(new HomeFragment());

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {

            Fragment fragment = null;

            int id = item.getItemId();

            switch (id) {
                case R.id.homeFragment:
                    fragment = new HomeFragment();
                    break;
                case R.id.profileFragment:
                    fragment = new ProfileFragment();
                    break;
                case R.id.logFragment:
                    fragment = new LogFragment();
                    break;
                case R.id.socialFragment:
                    fragment = new SocialFragment();
                    break;
                case R.id.libraryFragment:
                    fragment = new LibraryFragment();
                    break;
            }

            return loadFragments(fragment);
        });


    }

    public boolean loadFragments(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, fragment)
                    .commit();
        }
        return true;
    }

    @Override
    public void onButtonClicked(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .commit();
    }
}

