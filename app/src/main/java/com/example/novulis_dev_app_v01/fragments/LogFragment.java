package com.example.novulis_dev_app_v01.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.novulis_dev_app_v01.R;
import com.example.novulis_dev_app_v01.adapters.LogRecyclerViewAdapter;
import com.example.novulis_dev_app_v01.adapters.RecyclerViewAdapter;
import com.example.novulis_dev_app_v01.model.Log;
import com.example.novulis_dev_app_v01.model.Profile;

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
    Button cancelBtn;
    Button saveBtn;

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
                final AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                View mView = getLayoutInflater().inflate(R.layout.dialog_log_input, null);

                // Find elements by id
                bookDropdown = mView.findViewById(R.id.bookDropdown);
                logDropdown = mView.findViewById(R.id.logDropdown);
                logAmount = mView.findViewById(R.id.logAmountEt);
                logNote = mView.findViewById(R.id.logNote);

                cancelBtn = mView.findViewById(R.id.cancelBtn);
                saveBtn = mView.findViewById(R.id.saveBtn);

                String[] items = new String[] {"Chapters", "Pages", "Books"};
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
                logDropdown.setAdapter(adapter);

                alert.setView(mView);
                final AlertDialog alertDialog = alert.create();

                alertDialog.setCanceledOnTouchOutside(true);

                // Set button listeners for the dialog

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
                                profile.getCurrentBook(),
                                Integer.parseInt(logAmount.getText().toString()),
                                logNote.getText().toString());

                        System.out.println("Log note: " + logNote.getText().toString());
                        profile.addLog(log);
                        profile.saveProfile(v.getContext());
                        profile.saveBookLog(v.getContext());

//                        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.logFragment);
//                        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
//                        fragmentTransaction.detach(currentFragment);
//                        fragmentTransaction.attach(currentFragment);
//                        fragmentTransaction.commit();

                        alertDialog.dismiss();
                    }

                });

                alertDialog.show();
            }
        });

        return v;
    }
}