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

public class PaintCustomisationFragment extends Fragment {

    private FragmentCallback callback;

    private ImageView bodyTab;
    private ImageView paintTab;
    private ImageView trailTab;

    public PaintCustomisationFragment() {
        // Required empty public constructor
    }

    public static PaintCustomisationFragment newInstance() {
        PaintCustomisationFragment fragment = new PaintCustomisationFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_paint_customisation, container, false);

        bodyTab = view.findViewById(R.id.shipTab);
        paintTab = view.findViewById(R.id.paintTab);
        trailTab = view.findViewById(R.id.trailTab);

        bodyTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new BodyCustomisationFragment();
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

        return view;
    }
}