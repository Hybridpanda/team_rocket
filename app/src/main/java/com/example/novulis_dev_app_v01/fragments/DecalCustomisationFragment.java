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

public class DecalCustomisationFragment extends Fragment {

    private FragmentCallback callback;

    private ImageView bodyTab;
    private ImageView paintTab;
    private ImageView trailTab;
    private ImageView colourSchemeIv;
    private ImageView shipPreview;
    private ImageView paintA;


    private boolean decalSelected = false;

    public DecalCustomisationFragment() {
        // Required empty public constructor
    }

    public static DecalCustomisationFragment newInstance() {
        DecalCustomisationFragment fragment = new DecalCustomisationFragment();
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
        View view = inflater.inflate(R.layout.fragment_decal_customisation, container, false);

        bodyTab = view.findViewById(R.id.shipTab);
        paintTab = view.findViewById(R.id.paintTab);
        trailTab = view.findViewById(R.id.trailTab);
        colourSchemeIv = view.findViewById(R.id.colourSchemeIv);
        shipPreview = view.findViewById(R.id.shipPreviewDecal);
        paintA = view.findViewById(R.id.paintA);


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

        colourSchemeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new PaintCustomisationFragment();
                callback.onButtonClicked(fragment);
            }
        });

        paintA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!decalSelected) {
                    paintA.setImageResource(R.drawable.shipcust_paint_b1_selected);
                    shipPreview.setImageResource(R.drawable.shipcust_default_paint_b1);
                    decalSelected = true;
                } else {
                    paintA.setImageResource(R.drawable.shipcust_paint_b1);
                    shipPreview.setImageResource(R.drawable.shipcust_ship_default);
                    decalSelected = false;
                }
            }
        });

        return view;
    }
}