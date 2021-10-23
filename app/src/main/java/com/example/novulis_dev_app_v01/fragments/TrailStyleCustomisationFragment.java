package com.example.novulis_dev_app_v01.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.novulis_dev_app_v01.R;
import com.example.novulis_dev_app_v01.callbacks.FragmentCallback;

public class TrailStyleCustomisationFragment extends Fragment {

    private FragmentCallback callback;

    private ImageView bodyTab;
    private ImageView paintTab;
    private ImageView trailTab;
    private ImageView trailColourIv;
    private ImageView trailStyleIv;
    private ImageView paintA;
    private ImageView shipPreview;
    private ImageView shipTrailPreview;

    private boolean trailSelected;

    public TrailStyleCustomisationFragment() {
        // Required empty public constructor
    }

    public static TrailStyleCustomisationFragment newInstance() {
        TrailStyleCustomisationFragment fragment = new TrailStyleCustomisationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentCallback) {
            callback = (FragmentCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentCallback");
        }
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
        View view = inflater.inflate(R.layout.fragment_trail_style_customisation, container, false);

        bodyTab = view.findViewById(R.id.shipTab);
        paintTab = view.findViewById(R.id.paintTab);
        trailTab = view.findViewById(R.id.trailTab);
        trailStyleIv = view.findViewById(R.id.trailStyleIv);
        trailColourIv = view.findViewById(R.id.trailColourIv);
        paintA = view.findViewById(R.id.paintA);
        shipPreview = view.findViewById(R.id.shipPreview);
        shipTrailPreview = view.findViewById(R.id.trailPreview);

        paintTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new PaintCustomisationFragment();
                callback.onButtonClicked(fragment);
            }
        });

        bodyTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new BodyCustomisationFragment();
                callback.onButtonClicked(fragment);
            }
        });

        trailStyleIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new TrailStyleCustomisationFragment();
                callback.onButtonClicked(fragment);
            }
        });

        paintA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!trailSelected) {
                    paintA.setImageResource(R.drawable.shipcust_trail_b1_selected);
                    //shipPreview.setImageResource(R.drawable.shipcust_default_with_bolt_trail);
                    shipPreview.setVisibility(View.INVISIBLE);
                    shipTrailPreview.setVisibility(View.VISIBLE);
                    trailSelected = true;
                } else {
                    paintA.setImageResource(R.drawable.shipcust_trail_b1);
                    //shipPreview.setImageResource(R.drawable.shipcust_ship_default);
                    shipPreview.setVisibility(View.VISIBLE);
                    shipTrailPreview.setVisibility(View.INVISIBLE);
                    trailSelected = false;
                }
            }
        });

        return view;
    }

}