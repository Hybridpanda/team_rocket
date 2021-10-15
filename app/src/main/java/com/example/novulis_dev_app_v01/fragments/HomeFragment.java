package com.example.novulis_dev_app_v01.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.novulis_dev_app_v01.R;
import com.example.novulis_dev_app_v01.model.Profile;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    Profile profile;
    TextView currentBookTv;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        profile = new Profile();
        //profile.saveProfile(view.getContext());
        profile.loadProfile(view.getContext());

        currentBookTv = view.findViewById(R.id.currentBookTv);
        currentBookTv.setText(profile.getCurrentBook());
        System.out.println(profile.getCurrentBook());

        return view;
    }
}