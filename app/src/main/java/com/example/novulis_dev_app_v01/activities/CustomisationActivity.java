package com.example.novulis_dev_app_v01.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import com.example.novulis_dev_app_v01.R;
import com.example.novulis_dev_app_v01.callbacks.FragmentCallback;
import com.example.novulis_dev_app_v01.fragments.BodyCustomisationFragment;

import android.os.Bundle;

public class CustomisationActivity extends AppCompatActivity implements FragmentCallback {

    private FragmentContainerView customisationContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customisation);

        customisationContainer = findViewById(R.id.customisationFragmentContainerView);

        getSupportFragmentManager().beginTransaction().replace(R.id.customisationFragmentContainerView, new BodyCustomisationFragment());


    }

    @Override
    public void onButtonClicked(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.customisationFragmentContainerView, fragment)
                .commit();
    }
}