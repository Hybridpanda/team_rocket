package com.example.novulis_dev_app_v01.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.novulis_dev_app_v01.R;

public class EragonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eragon);

        ImageView nextofBtn = findViewById(R.id.percyJView);
        nextofBtn.setOnClickListener(view -> {
            Intent i = new Intent(this, PercyJacksonActivity.class);
            startActivity(i);
        });

        ImageView backBtn = findViewById(R.id.ulysView);
        backBtn.setOnClickListener(view -> {
            Intent i = new Intent(this, UlyssesActivity.class);
            startActivity(i);
        });
    }
}
