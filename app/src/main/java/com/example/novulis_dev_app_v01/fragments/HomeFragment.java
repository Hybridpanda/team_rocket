package com.example.novulis_dev_app_v01.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.novulis_dev_app_v01.R;
import com.example.novulis_dev_app_v01.model.Log;
import com.example.novulis_dev_app_v01.model.Profile;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    Profile profile;
    TextView currentBookTv;
    ImageView rocketShip;

    ProgressBar pagesReadProgressBar;
    ProgressBar readingTimeProgressBar;
    ProgressBar fuelProgressBar;
    ProgressBar currentGoalProgress;
    TextView goalText;

    TextView tvReadingTime;
    TextView tvPagesRead;
    TextView tvFuel;
    TextView tvGoalProgress;

    Button changeGoalBtn;
    Button cancelBtn;
    Button saveBtn;
    Spinner intervalDropdown;
    DiscreteSeekBar goalValueSeekBar;

    FragmentActivity mContext;

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
    public void onAttach(Activity activity) {
        mContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        profile = new Profile();
        profile.loadProfile(view.getContext());

        rocketShip = view.findViewById(R.id.imageView2);
        final SpringAnimation imageSpring = new SpringAnimation(rocketShip, DynamicAnimation.ROTATION, 0);
        imageSpring.setStartValue(50f);
        imageSpring.setStartVelocity(70f);
        SpringForce springForce = new SpringForce();
        springForce.setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
        springForce.setFinalPosition(0f);
        springForce.setStiffness(SpringForce.STIFFNESS_LOW);
        imageSpring.setSpring(springForce);
        imageSpring.start();

        currentBookTv = view.findViewById(R.id.currentBookTv);
        currentBookTv.setText(profile.getCurrentBookTitle());

        pagesReadProgressBar = view.findViewById(R.id.pagesReadProgressBar);
        readingTimeProgressBar = view.findViewById(R.id.readingTimeProgressBar);
        fuelProgressBar = view.findViewById(R.id.progressBar3);
        currentGoalProgress = view.findViewById(R.id.currentGoalProgress);

        tvReadingTime = view.findViewById(R.id.textView11);
        tvPagesRead = view.findViewById(R.id.textView12);
        tvFuel = view.findViewById(R.id.textView13);
        tvGoalProgress = view.findViewById(R.id.textView14);

        changeGoalBtn = view.findViewById(R.id.changeGoalBtn);
        goalText = view.findViewById(R.id.textView5);

        goalText.setText("I want to read " + profile.getGoalValue() + " pages each " + profile.getGoalInterval());
        pagesReadProgressBar.setMax(100);
        fuelProgressBar.setMax(100);
        readingTimeProgressBar.setMax(5);
        currentGoalProgress.setMax(100);

        pagesReadProgressBar.setProgress(profile.getPagesRead());

        if (profile.getPagesRead() > 60) {

            fuelProgressBar.setProgress(100);
            tvFuel.setText("100%");

            readingTimeProgressBar.setProgress(5);
            tvReadingTime.setText("5h");

            currentGoalProgress.setProgress(100);
            tvPagesRead.setText(profile.getPagesRead() + "\npgs");
            tvGoalProgress.setText("100\n/100");
        } else {
            fuelProgressBar.setProgress(70);
            tvFuel.setText("70%");

            readingTimeProgressBar.setProgress(4);
            tvReadingTime.setText("4h");

            currentGoalProgress.setProgress(60);
            tvPagesRead.setText(profile.getPagesRead() + "\npgs");
            tvGoalProgress.setText("60\n/100");
        }

        changeGoalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext(), R.style.CustomAlertDialog);
                View mView = getLayoutInflater().inflate(R.layout.dialog_change_goal, null);

                cancelBtn = mView.findViewById(R.id.cancelBtn);
                saveBtn = mView.findViewById(R.id.saveBtn);
                intervalDropdown = mView.findViewById(R.id.intervalDropdown);
                goalValueSeekBar = mView.findViewById(R.id.pageSeekBar);

                // Set up the drop down selectors
                String[] items = new String[] {"Day", "Week", "Month"};
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_dropdown_item, items);
                adapter.setDropDownViewResource(R.layout.spinner_list);
                intervalDropdown.setAdapter(adapter);

                // Create the log input pop up
                alert.setView(mView);
                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(true);

                // Set button listeners for the dialog

                final AlertDialog[] unlockDialog = new AlertDialog[1];

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        profile.setGoalValue(goalValueSeekBar.getProgress());
                        profile.setGoalInterval(intervalDropdown.getSelectedItem().toString());
                        profile.saveProfile(view.getContext());
                        profile.saveLibrary(view.getContext());

                        alertDialog.dismiss();

                        Fragment frg = new HomeFragment();
                        final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.fragmentContainerView, frg);
                                ft.commit();

                    }

                });
                alertDialog.show();
            }
        });

        return view;
    }
}