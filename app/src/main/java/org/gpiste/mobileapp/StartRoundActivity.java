package org.gpiste.mobileapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

public class StartRoundActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_round);

        Button laajisButton = findViewById(R.id.btnLaajis);
        laajisButton.setOnClickListener(v -> showConfirmation("Laajis"));

        Button vesalaButton = findViewById(R.id.btnVesala);
        vesalaButton.setOnClickListener(v -> showConfirmation("Vesalan Monttu"));

        Button hiiskaButton = findViewById(R.id.btnHiiska);
        hiiskaButton.setOnClickListener(v -> showConfirmation("Hiiska"));

        Button keljoButton = findViewById(R.id.btnKeljo);
        keljoButton.setOnClickListener(v -> showConfirmation("Keljonkangas"));

        Button vlandiaButton = findViewById(R.id.btnVLandia);
        vlandiaButton.setOnClickListener(v -> showConfirmation("Viherlandia"));

    }

    private void showConfirmation(String courseName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Start round at " + courseName + "?")
                .setPositiveButton("Yes", (dialog, id) -> startRound(courseName))
                .setNegativeButton("No", (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void startRound(String courseName) {
        Intent intent = new Intent(StartRoundActivity.this, RoundActivity.class);
        intent.putExtra("courseName", courseName);
        startActivity(intent);
        finish();
    }
}