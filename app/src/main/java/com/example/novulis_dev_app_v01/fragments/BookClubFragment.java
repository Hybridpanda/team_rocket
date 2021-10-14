package com.example.novulis_dev_app_v01.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.novulis_dev_app_v01.R;
import com.example.novulis_dev_app_v01.activities.EragonActivity;
import com.example.novulis_dev_app_v01.activities.InProgressActivity;
import com.example.novulis_dev_app_v01.activities.PercyJacksonActivity;
import com.example.novulis_dev_app_v01.activities.UlyssesActivity;

public class BookClubFragment extends Fragment {

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