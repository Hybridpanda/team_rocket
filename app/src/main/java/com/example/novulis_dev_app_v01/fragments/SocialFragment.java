package com.example.novulis_dev_app_v01.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.novulis_dev_app_v01.R;
import com.example.novulis_dev_app_v01.activities.BookClubsActivity;

public class SocialFragment extends Fragment {

    private Button clubsBtn;

    public SocialFragment() {
        // Required empty public constructor
    }

    public static SocialFragment newInstance(String param1, String param2) {
        SocialFragment fragment = new SocialFragment();
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

        View view = inflater.inflate(R.layout.fragment_social, container, false);

        clubsBtn = view.findViewById(R.id.clubsBtn);

        clubsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), BookClubsActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}