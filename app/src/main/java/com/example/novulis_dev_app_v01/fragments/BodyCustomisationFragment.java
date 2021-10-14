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
import com.example.novulis_dev_app_v01.model.Profile;

public class BodyCustomisationFragment extends Fragment {

    private FragmentCallback callback;

    private ImageView bodyTab;
    private ImageView paintTab;
    private ImageView trailTab;
    private ImageView flyingCarIv;
    private ImageView shipPreview;

    private Profile profile;

    public BodyCustomisationFragment() {
        // Required empty public constructor
    }

    public static BodyCustomisationFragment newInstance() {
        BodyCustomisationFragment fragment = new BodyCustomisationFragment();
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

        View view = inflater.inflate(R.layout.fragment_body_customisation, container, false);

        profile = new Profile();
        profile.loadProfile(view.getContext());

        bodyTab = view.findViewById(R.id.shipTab);
        paintTab = view.findViewById(R.id.paintTab);
        trailTab = view.findViewById(R.id.trailTab);
        flyingCarIv = view.findViewById(R.id.bodyItem4);
        shipPreview = view.findViewById(R.id.shipPreview);

        paintTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new PaintCustomisationFragment();
                callback.onButtonClicked(fragment);
            }
        });

        trailTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new TrailCustomisationFragment();
                callback.onButtonClicked(fragment);
            }
        });

        flyingCarIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shipPreview.setImageResource(R.drawable.flying_car_preview);
                profile.setCurrentShip("Flying Car");
                profile.saveProfile(view.getContext());
            }
        });

        return view;
    }
}