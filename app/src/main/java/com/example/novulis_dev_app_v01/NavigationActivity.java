package com.example.novulis_dev_app_v01;

import static android.os.FileUtils.copy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.novulis_dev_app_v01.fragments.HomeFragment;
import com.example.novulis_dev_app_v01.fragments.LibraryFragment;
import com.example.novulis_dev_app_v01.fragments.LogFragment;
import com.example.novulis_dev_app_v01.fragments.ProfileFragment;
import com.example.novulis_dev_app_v01.fragments.SocialFragment;
import com.example.novulis_dev_app_v01.model.Book;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class NavigationActivity extends AppCompatActivity {

    private static final int IO_BUFFER_SIZE = 4 * 1024;
    private static final String TAG = "TAG";
    // Nav bar variables
    BottomNavigationView bottomNavigationView;
    NavigationBarItemView navigationBarItemView;
    FrameLayout frameLayout;

    // Library view variables
    String testBookJson = "https://www.googleapis.com/books/v1/volumes?q=isbn:9780007128457";
    RecyclerView recyclerView;
    ArrayList<Book> library;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);


        // Load the fragments and set listener for nav bar
        loadFragments(new HomeFragment());
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

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
            }
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



}

