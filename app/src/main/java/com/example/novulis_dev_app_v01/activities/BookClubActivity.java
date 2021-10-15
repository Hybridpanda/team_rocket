package com.example.novulis_dev_app_v01.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.example.novulis_dev_app_v01.R;

public class BookClubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_club);

       ImageView planetBtn = findViewById(R.id.harryView);
        planetBtn.setOnClickListener(view -> {
            Intent i = new Intent(this, InProgressActivity.class);
            startActivity(i);
        });
        ImageView ulysBtn = findViewById(R.id.ulyssesView);
        ulysBtn.setOnClickListener(view -> {
            Intent i = new Intent(this, UlyssesActivity.class);
            startActivity(i);
        });
        ImageView eragBtn = findViewById(R.id.EragonView);
        eragBtn.setOnClickListener(view -> {
            Intent i = new Intent(this, EragonActivity.class);
            startActivity(i);
        });
        ImageView PercyBtn = findViewById(R.id.PercyView);
        PercyBtn.setOnClickListener(view -> {
            Intent i = new Intent(this, PercyJacksonActivity.class);
            startActivity(i);
        });
    }
}