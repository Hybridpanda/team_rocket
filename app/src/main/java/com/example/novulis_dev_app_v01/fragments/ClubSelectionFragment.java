package com.example.novulis_dev_app_v01.fragments;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;

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
        ObjectAnimator translateAnimation = ObjectAnimator.ofFloat(teamRocketBtn, view.TRANSLATION_Y, 0);
        translateAnimation.setRepeatCount(1);
        translateAnimation.setRepeatMode( ValueAnimator.REVERSE);
        Log.d("done", "done");
        teamRocketBtn = view.findViewById(R.id.teamRocketBtn);

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