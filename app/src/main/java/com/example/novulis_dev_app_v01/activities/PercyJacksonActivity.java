package com.example.novulis_dev_app_v01.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.novulis_dev_app_v01.R;

public class PercyJacksonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percy_jackson);

        ImageView nextinBtn = findViewById(R.id.next);
        nextinBtn.setOnClickListener(view -> {
            Intent i = new Intent(this, InProgressActivity.class);
            startActivity(i);
        });
        ImageView backishBtn = findViewById(R.id.erBack);
        backishBtn.setOnClickListener(view -> {
            Intent i = new Intent(this, EragonActivity.class);
            startActivity(i);
        });
    }
}