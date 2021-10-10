package com.example.novulis_dev_app_v01.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.novulis_dev_app_v01.R;

public class ClubsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clubs);

        Button rocketBtn = findViewById(R.id.teamRocketBtn);
        rocketBtn.setOnClickListener(view -> {
            Intent i = new Intent(this, BookClubActivity.class);
            Log.d("info", String.valueOf(i));
            startActivity(i);
        });
    }

}