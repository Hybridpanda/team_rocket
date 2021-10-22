package com.example.novulis_dev_app_v01.fragments;

import android.os.Bundle;

import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.novulis_dev_app_v01.R;
import com.example.novulis_dev_app_v01.model.Profile;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    Profile profile;
    TextView currentBookTv;
    ImageView rocketShip;

    ProgressBar pagesReadProgressBar;
    ProgressBar readingTimeProgressBar;
    ProgressBar fuelProgressBar;
    ProgressBar currentGoalProgress;

    TextView tvReadingTime;
    TextView tvPagesRead;
    TextView tvFuel;
    TextView tvGoalProgress;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        profile = new Profile();
        //profile.saveProfile(view.getContext());
        profile.loadProfile(view.getContext());

        rocketShip = view.findViewById(R.id.imageView2);
        final SpringAnimation imageSpring = new SpringAnimation(rocketShip, DynamicAnimation.ROTATION, 0);
        imageSpring.setStartValue(50f);
        imageSpring.setStartVelocity(70f);
        SpringForce springForce = new SpringForce();
        springForce.setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
        springForce.setFinalPosition(0f);
        springForce.setStiffness(SpringForce.STIFFNESS_LOW);
        imageSpring.setSpring(springForce);
        imageSpring.start();

        currentBookTv = view.findViewById(R.id.currentBookTv);
        currentBookTv.setText(profile.getCurrentBookTitle());

        pagesReadProgressBar = view.findViewById(R.id.pagesReadProgressBar);
        readingTimeProgressBar = view.findViewById(R.id.readingTimeProgressBar);
        fuelProgressBar = view.findViewById(R.id.progressBar3);
        currentGoalProgress = view.findViewById(R.id.currentGoalProgress);

        tvReadingTime = view.findViewById(R.id.textView11);
        tvPagesRead = view.findViewById(R.id.textView12);
        tvFuel = view.findViewById(R.id.textView13);
        tvGoalProgress = view.findViewById(R.id.textView14);

        pagesReadProgressBar.setMax(100);
        fuelProgressBar.setMax(100);
        readingTimeProgressBar.setMax(5);
        currentGoalProgress.setMax(100);

        pagesReadProgressBar.setProgress(profile.getPagesRead());

        if (profile.getPagesRead() > 60) {

            fuelProgressBar.setProgress(100);
            tvFuel.setText("100%");

            readingTimeProgressBar.setProgress(5);
            tvReadingTime.setText("5h");

            currentGoalProgress.setProgress(100);
            tvPagesRead.setText(profile.getPagesRead() + "\npgs");
            tvGoalProgress.setText("100\n/100");
        } else {
            fuelProgressBar.setProgress(70);
            tvFuel.setText("70%");

            readingTimeProgressBar.setProgress(4);
            tvReadingTime.setText("4h");

            currentGoalProgress.setProgress(60);
            tvPagesRead.setText(profile.getPagesRead() + "\npgs");
            tvGoalProgress.setText("60\n/100");
        }

        return view;
    }
}