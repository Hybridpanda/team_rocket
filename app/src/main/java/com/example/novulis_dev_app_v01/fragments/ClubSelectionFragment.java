package com.example.novulis_dev_app_v01.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_club_selection, container, false);

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