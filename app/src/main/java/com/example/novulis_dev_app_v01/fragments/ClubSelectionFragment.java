package com.example.novulis_dev_app_v01.fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FlingAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.novulis_dev_app_v01.R;
import com.example.novulis_dev_app_v01.activities.BookClubsActivity;

public class ClubSelectionFragment extends Fragment {

    ImageView teamRocketBtn;
    ImageView bakiBtn;
    ConstraintLayout constraint1;
    ConstraintLayout constraint2;

    public ClubSelectionFragment() {
        // Required empty public constructor
    }


    public static ClubSelectionFragment newInstance() {
        ClubSelectionFragment fragment = new ClubSelectionFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_club_selection, container, false);
        teamRocketBtn = view.findViewById(R.id.teamRocketBtn);
        bakiBtn = view.findViewById(R.id.imageView17);
        constraint1 = view.findViewById(R.id.constraint1);
        constraint2 = view.findViewById(R.id.constraint2);

        final SpringAnimation imageSpring = new SpringAnimation(constraint1, DynamicAnimation.TRANSLATION_X, 0);
        imageSpring.setStartValue(50f);
        imageSpring.setStartVelocity(70f);
        SpringForce springForce = new SpringForce();
        springForce.setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
        springForce.setFinalPosition(0f);
        springForce.setStiffness(SpringForce.STIFFNESS_MEDIUM);
        imageSpring.setSpring(springForce);
        imageSpring.start();

        final SpringAnimation image2Spring = new SpringAnimation(constraint2, DynamicAnimation.TRANSLATION_X, 0);
        image2Spring.setStartValue(50f);
        image2Spring.setStartVelocity(70f);
        SpringForce spring2Force = new SpringForce();
        spring2Force.setDampingRatio(spring2Force.DAMPING_RATIO_HIGH_BOUNCY);
        spring2Force.setFinalPosition(0f);
        spring2Force.setStiffness(spring2Force.STIFFNESS_LOW);
        image2Spring.setSpring(spring2Force);
        image2Spring.start();

        Log.d("done", "done");
        teamRocketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), BookClubsActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}