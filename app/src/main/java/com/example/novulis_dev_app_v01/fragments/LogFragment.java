package com.example.novulis_dev_app_v01.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.novulis_dev_app_v01.R;
import com.example.novulis_dev_app_v01.adapters.LogRecyclerViewAdapter;
import com.example.novulis_dev_app_v01.model.Book;
import com.example.novulis_dev_app_v01.model.Log;
import com.example.novulis_dev_app_v01.model.Profile;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;


public class LogFragment extends Fragment {

    Profile profile;

    // Layout variables
    Button newLogBtn;

    // Pop-up variables
    Spinner bookDropdown;
    Spinner logDropdown;
    EditText logAmount;
    EditText logNote;
    EditText durationHours;
    EditText durationMinutes;
    Button cancelBtn;
    Button saveBtn;
    Button closeBtn;
    ImageView flyingCarIv;
    DiscreteSeekBar pagesSeekBar;

    // Recycler view for log display
    RecyclerView logRecyclerView;

    public LogFragment() {
        // Required empty public constructor
    }

    public static LogFragment newInstance() {
        LogFragment fragment = new LogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void openLogDialog(View view) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
        View mView = getLayoutInflater().inflate(R.layout.dialog_log_input, null);

        // Find elements by id

        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();

        // Set button listeners for the dialog

        alertDialog.show();
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
        View v = inflater.inflate(R.layout.fragment_log, container, false);
        logDropdown = v.findViewById(R.id.logDropdown);

        profile = new Profile();
        profile.loadProfile(v.getContext());
        profile.loadLibrary(v.getContext());
        profile.loadBookLog(v.getContext());

        logRecyclerView = v.findViewById(R.id.logRecyclerView);

        ArrayList<Log> bookLog = profile.getBookLog();
        Collections.reverse(bookLog);
        logRecyclerView.setAdapter(new LogRecyclerViewAdapter(bookLog, v.getContext()));
        logRecyclerView.setHasFixedSize(true);
        logRecyclerView.setLayoutManager(new LinearLayoutManager(v.getContext(), RecyclerView.VERTICAL, false));

        newLogBtn = v.findViewById(R.id.newLogBtn);
        newLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext(), R.style.CustomAlertDialog);
                View mView = getLayoutInflater().inflate(R.layout.dialog_log_input, null);

                final AlertDialog.Builder unlockAlert = new AlertDialog.Builder(view.getContext(), R.style.CustomAlertDialog);
                View unlockView = getLayoutInflater().inflate(R.layout.dialog_unlock_car, null);

                // Find the elements for the unlock pop up
                closeBtn = unlockView.findViewById(R.id.closeBtn);
                flyingCarIv = unlockView.findViewById(R.id.trinketIv);

                // Find elements by id
                pagesSeekBar = mView.findViewById(R.id.pageSeekBar);
                bookDropdown = mView.findViewById(R.id.bookDropdown);
                logDropdown = mView.findViewById(R.id.logDropdown);
                logAmount = mView.findViewById(R.id.logAmountEt);
                logNote = mView.findViewById(R.id.logNote);
                durationHours = mView.findViewById(R.id.durationHoursEt);
                durationMinutes = mView.findViewById(R.id.durationMinutesEt);
                int durationTotalMinutes = 60 * Integer.parseInt(durationHours.getText().toString()) + Integer.parseInt(durationMinutes.getText().toString());
                cancelBtn = mView.findViewById(R.id.cancelBtn);
                saveBtn = mView.findViewById(R.id.saveBtn);

                // Set up the drop down selectors
                String[] items = new String[] {"Chapters", "Pages", "Books"};
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
                logDropdown.setAdapter(adapter);

                String[] bookTitles = profile.getBookTitles();
                ArrayAdapter<String> titleAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, bookTitles);
                bookDropdown.setAdapter(titleAdapter);

                //Book currentBook = profile.getCurrentBook();
                pagesSeekBar.setMax(100);
                pagesSeekBar.setProgress(20);

                // Create the animation for the unlock
//                RotateAnimation forwardAnimation = new RotateAnimation(0, 30f,
//                        Animation.RELATIVE_TO_SELF, 0.5f,
//                        Animation.RELATIVE_TO_SELF, 0.5f);
//
//                RotateAnimation backwardAnimation = new RotateAnimation(30f, 0f,
//                        Animation.RELATIVE_TO_SELF, 0.5f,
//                        Animation.RELATIVE_TO_SELF, 0.5f);
//
//                forwardAnimation.setInterpolator(new LinearInterpolator());
//                forwardAnimation.setDuration(2000);
//                forwardAnimation.setRepeatCount(Animation.INFINITE);
//
//                unlockView.findViewById(R.id.flyingCarIv).startAnimation(forwardAnimation);


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

                        Log log = new Log(Calendar.getInstance().getTime(),
                                bookDropdown.getSelectedItem().toString(),
                                pagesSeekBar.getProgress(),
                                logNote.getText().toString(),
                                durationTotalMinutes);

                        System.out.println("Log note: " + logNote.getText().toString());
                        profile.addLog(log);
                        profile.setPagesRead(100);
                        profile.saveProfile(v.getContext());
                        profile.saveBookLog(v.getContext());
                        profile.saveLibrary(v.getContext());

                        alertDialog.dismiss();

                        // Create the unlock pop up for the Flying Car
                        unlockAlert.setView(unlockView);
                        unlockDialog[0] = unlockAlert.create();
                        unlockDialog[0].show();
                        final SpringAnimation imageSpring = new SpringAnimation(flyingCarIv, DynamicAnimation.ROTATION_Y, 0);
                        imageSpring.setStartValue(1000f);
                        imageSpring.setStartVelocity(100f);
                        SpringForce springForce = new SpringForce();
                        springForce.setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
                        springForce.setFinalPosition(0f);
                        springForce.setStiffness(SpringForce.STIFFNESS_LOW);
                        imageSpring.setSpring(springForce);
                        imageSpring.start();

                    }

                });

                alertDialog.show();

                closeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        unlockDialog[0].dismiss();
                    }
                });
            }
        });

        return v;
    }
}