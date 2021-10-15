package com.example.novulis_dev_app_v01.fragments;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.novulis_dev_app_v01.R;
import com.example.novulis_dev_app_v01.activities.BookClubsActivity;
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
    private boolean shipCheck = true;

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



        if (profile.getCurrentShip().equals("Flying Car") && shipCheck) {
            createNotificationChannel(view);
            System.out.println("Notification not working");


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

    private void createNotificationChannel(View view) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Chat_Message";
            String description = "Cool chat message";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
            channel.enableVibration(true);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            //NotificationManager notificationManager = view.getContext().getSystemService(NotificationManager.class);
            NotificationManager notificationManager = (NotificationManager) view.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);

            Intent intent = new Intent(view.getContext(), BookClubsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(view.getContext(), 0, intent, 0);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(view.getContext(), channel.getId())
                    .setSmallIcon(R.drawable.ic_club)
                    .setContentTitle("Team Rocket")
                    .setContentText("Hey nice ship!")
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setAutoCancel(true)
                    .setVibrate(new long[] {10})
                    .setFullScreenIntent(pendingIntent, true);

            notificationManager.notify(1, builder.build());
            shipCheck = false;
        }
    }

}