package com.example.novulis_dev_app_v01.fragments;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;

import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.novulis_dev_app_v01.R;

public class ChatFragment extends Fragment {

    private int messageIncrement;
    LinearLayout chatLayout1;
    LinearLayout chatLayout2;
    LinearLayout chatLayout3;
    LinearLayout chatLayout4;

    LinearLayout[] layoutArray;

    Button sendMessageBtn;

    EditText messageEt;
    TextView chatBubble2;
    TextView chatBubble4;

    public ChatFragment() {
        // Required empty public constructor
    }

    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        chatLayout1 = view.findViewById(R.id.chatLayout1);
        chatLayout2 = view.findViewById(R.id.chatLayout2);
        chatLayout3 = view.findViewById(R.id.chatLayout3);
        chatLayout4 = view.findViewById(R.id.chatLayout4);

        sendMessageBtn = view.findViewById(R.id.sendBtn);
        messageEt = view.findViewById(R.id.messageEt);
        chatBubble2 = view.findViewById(R.id.chatBubble2);
        chatBubble4 = view.findViewById(R.id.chatBubble4);

        chatLayout2.setVisibility(View.INVISIBLE);
        chatLayout3.setVisibility(View.INVISIBLE);
        chatLayout4.setVisibility(View.INVISIBLE);

        SpringAnimation avatar1Animation = createNewAnimation(chatLayout1);
        avatar1Animation.start();

        messageIncrement = 0;
        layoutArray = new LinearLayout[3];
        layoutArray[0] = chatLayout2;
        layoutArray[1] = chatLayout3;
        layoutArray[2] = chatLayout4;

        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm = (InputMethodManager)view.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(messageEt.getWindowToken(), 0);

                if (messageIncrement == 0) {
                    chatBubble2.setText(messageEt.getText());
                } else if (messageIncrement == 2) {
                    chatBubble4.setText(messageEt.getText());
                }

                messageEt.setText("");
                System.out.println("Message Incremenet: " + messageIncrement);

                layoutArray[messageIncrement].setVisibility(View.VISIBLE);
                SpringAnimation chatAnimation = createNewAnimation(layoutArray[messageIncrement]);
                chatAnimation.start();
                messageIncrement++;
            }
        });

        return view;
    }

    public SpringAnimation createNewAnimation(View v) {
        SpringAnimation imageSpring = new SpringAnimation(v, DynamicAnimation.TRANSLATION_Y, 0);
        imageSpring.setStartValue(10000f);
        imageSpring.setStartVelocity(10f);
        SpringForce springForce = new SpringForce();
        springForce.setDampingRatio(SpringForce.DAMPING_RATIO_NO_BOUNCY);
        springForce.setFinalPosition(0f);
        springForce.setStiffness(SpringForce.STIFFNESS_VERY_LOW);
        imageSpring.setSpring(springForce);
        return imageSpring;
    }
}