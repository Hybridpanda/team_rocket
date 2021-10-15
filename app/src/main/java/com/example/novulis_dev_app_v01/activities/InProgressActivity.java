package com.example.novulis_dev_app_v01.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.example.novulis_dev_app_v01.R;

public class InProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_progress);

        ImageView backBtn = findViewById(R.id.back);
        backBtn.setOnClickListener(view -> {
            Intent i = new Intent(this, PercyJacksonActivity.class);
            startActivity(i);
        });
    }
}