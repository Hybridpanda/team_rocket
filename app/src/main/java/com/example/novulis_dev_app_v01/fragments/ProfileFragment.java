package com.example.novulis_dev_app_v01.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.novulis_dev_app_v01.R;
import com.example.novulis_dev_app_v01.activities.CustomisationActivity;
import com.example.novulis_dev_app_v01.activities.TrinketsActivity;
import com.example.novulis_dev_app_v01.model.Profile;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private Button trinketsBtn;
    private ImageView shipCustomisation;
    private Profile profile;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        trinketsBtn = view.findViewById(R.id.trinketsBtn);
        shipCustomisation = view.findViewById(R.id.imageView42);
        profile = new Profile();
        profile.loadProfile(view.getContext());
        if(profile.getCurrentShip().equals("Flying Car")) {
            shipCustomisation.setImageResource(R.drawable.flying_car);
        }

        trinketsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TrinketsActivity.class);
                startActivity(intent);
            }
        });

        shipCustomisation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CustomisationActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}