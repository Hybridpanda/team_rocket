package com.example.novulis_dev_app_v01.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.novulis_dev_app_v01.R;
import com.example.novulis_dev_app_v01.fragments.BookClubFragment;

public class UlyssesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ulysses);

        ImageView nextBtn = findViewById(R.id.EraView);
        nextBtn.setOnClickListener(view -> {
            Intent i = new Intent(this, EragonActivity.class);
            startActivity(i);
        });
    }
}