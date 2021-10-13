package com.example.novulis_dev_app_v01.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.novulis_dev_app_v01.R;

public class TrinketsActivity extends AppCompatActivity {

    // Trinket list items
    private ImageView trinketA;
    private ImageView trinketB;
    private ImageView trinketC;

    // Trinket pop-up elements
    private Button closeBtn;
    private ImageView trinketIv;
    private TextView unlockText;
    private TextView trinketTitle;
    private TextView trinketText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trinkets);

        trinketA = findViewById(R.id.trinketA);
        trinketB = findViewById(R.id.trinketB);
        trinketC = findViewById(R.id.trinketC);

        final AlertDialog.Builder trinketPopup = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        View unlockView = getLayoutInflater().inflate(R.layout.dialog_unlock_car, null);

        // Find the elements for the unlock pop up
        closeBtn = unlockView.findViewById(R.id.closeBtn);
        trinketIv = unlockView.findViewById(R.id.trinketIv);
        unlockText = unlockView.findViewById(R.id.textView34);
        trinketTitle = unlockView.findViewById(R.id.trinketTitle);
        trinketText = unlockView.findViewById(R.id.trinketText);

        final AlertDialog[] trinket = new AlertDialog[1];
        trinketPopup.setView(unlockView);
        trinket[0] = trinketPopup.create();

        trinketA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trinketText.setText("Awarded for participating in the 20th Anniversary of Harry Potter and the Philosopher's Stone Exploration Challenge.\n" +
                        "\n" +
                        "Alchemic proof that friendship can transmute into bravery as, \"it takes a great deal of bravery to stand up to our enemies, but just as much to stand up to our friends.\"");
                unlockText.setText("Philosopher's Stone");
                trinketTitle.setText("");
                trinketIv.setImageResource(R.drawable.trinketitem_philstone);

                trinket[0].show();
            }
        });

        trinketB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trinketText.setText("Found by completing Dance, Dance, Dance by Haruki Murakami.\n" +
                        "\n" +
                        "A mantle worn by an enigmatic fellow. Merely being near it reminds you of a cryptic murmur, \'Yougottadance. Aslongasthemusicplays. Don'teventhinkwhy…\'");
                unlockText.setText("Odd Sheepskin");
                trinketTitle.setText("");
                trinketIv.setImageResource(R.drawable.trinketitem_sheepskin);
//                trinket[0] = trinketPopup.create();
                trinket[0].show();
            }
        });

        trinketC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                trinket[0] = trinketPopup.create();
                trinket[0].show();
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trinket[0].dismiss();
            }
        });

    }
}