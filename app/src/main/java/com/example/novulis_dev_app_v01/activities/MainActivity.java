package com.example.novulis_dev_app_v01.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.novulis_dev_app_v01.R;
import com.example.novulis_dev_app_v01.model.Profile;

public class MainActivity extends AppCompatActivity {

    Profile profile = new Profile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the library file with a set list of books
        profile.createLibrary("fantasy", getApplicationContext());
        profile.saveProfile(this);

        Button loginBtn = (Button)findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
            startActivity(intent);
        });
    }


}