package com.example.novulis_dev_app_v01.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.example.novulis_dev_app_v01.R;
import com.example.novulis_dev_app_v01.activities.EragonActivity;
import com.example.novulis_dev_app_v01.activities.InProgressActivity;
import com.example.novulis_dev_app_v01.activities.PercyJacksonActivity;
import com.example.novulis_dev_app_v01.activities.UlyssesActivity;
import com.example.novulis_dev_app_v01.model.OrbitAnimation;
import com.example.novulis_dev_app_v01.model.Profile;

public class BookClubFragment extends Fragment {

    private ImageView orbitingShipIv;
    private Profile profile;

    public BookClubFragment() {
        // Required empty public constructor
    }

    public static BookClubFragment newInstance() {
        BookClubFragment fragment = new BookClubFragment();
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
        View view = inflater.inflate(R.layout.fragment_book_club, container, false);
        // Inflate the layout for this fragment

        // Load the profile
        profile = new Profile();
        profile.loadProfile(view.getContext());

        // Set the ship to the users currently selected ship
        orbitingShipIv = view.findViewById(R.id.orbitingShipIv);
        if (profile.getCurrentShip().equals("Flying Car")) {
            orbitingShipIv.setImageResource(R.drawable.flying_car_preview);
        } else {
            orbitingShipIv.setImageResource(R.drawable.clipart2259762);
        }


        // Create and run the ship orbit animation to signify where the user currently is in their book club
        Animation rotationOrbit = AnimationUtils.loadAnimation(view.getContext(), R.anim.orbit_animation);
        rotationOrbit.setInterpolator(new LinearInterpolator());
        rotationOrbit.setFillBefore(false);
        rotationOrbit.setFillAfter(true);
        orbitingShipIv.startAnimation(rotationOrbit);

        ImageView planetBtn = view.findViewById(R.id.harryView);
        planetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), InProgressActivity.class);
                startActivity(i);
            }
        });
        ImageView ulysBtn = view.findViewById(R.id.ulyssesView);
        ulysBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent i = new Intent(getActivity(), UlyssesActivity.class);
                startActivity(i);
            }
        });
        ImageView eragBtn = view.findViewById(R.id.EragonView);
        eragBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), EragonActivity.class);
                startActivity(i);
            }
        });
        ImageView PercyBtn = view.findViewById(R.id.PercyView);
        PercyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent i = new Intent(getActivity(), PercyJacksonActivity.class);
            startActivity(i);
            }
        });

        return view;
    }
}