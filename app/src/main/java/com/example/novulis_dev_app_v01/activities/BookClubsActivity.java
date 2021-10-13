package com.example.novulis_dev_app_v01.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;

import com.example.novulis_dev_app_v01.R;
import com.example.novulis_dev_app_v01.fragments.BookClubFragment;
import com.example.novulis_dev_app_v01.fragments.ChatFragment;
import com.example.novulis_dev_app_v01.fragments.GroupProgressFragment;
import com.example.novulis_dev_app_v01.fragments.HomeFragment;
import com.example.novulis_dev_app_v01.fragments.LibraryFragment;
import com.example.novulis_dev_app_v01.fragments.LogFragment;
import com.example.novulis_dev_app_v01.fragments.ProfileFragment;
import com.example.novulis_dev_app_v01.fragments.SocialFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BookClubsActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_clubs);

        // Copied Variables
        Context mContext = this;



        bottomNavigationView = findViewById(R.id.clubsNavigationView);

        // Load the fragments and set listener for nav bar
        loadFragments(new BookClubFragment());
        bottomNavigationView.setSelectedItemId(R.id.bookClubFragment);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            Fragment fragment = null;

            int id = item.getItemId();

            switch (id) {
                case R.id.groupProgressFragment:
                    fragment = new GroupProgressFragment();
                    break;
                case R.id.bookClubFragment:
                    fragment = new BookClubFragment();
                    break;
                case R.id.chatFragment:
                    fragment = new ChatFragment();
                    break;
            }

            return loadFragments(fragment);
        });
    }

    public boolean loadFragments(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.clubsContainerView, fragment)
                    .commit();
        }
        return true;
    }
}