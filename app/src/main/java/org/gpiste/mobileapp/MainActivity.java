package org.gpiste.mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = findViewById(R.id.btnStart);
        startButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,StartRoundActivity.class);
            startActivity(intent);
        });
        Button courseButton = findViewById(R.id.btnCourses);
        courseButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,CourseActivity.class);
            startActivity(intent);
        });
        Button scoreButton = findViewById(R.id.btnScores);
        scoreButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,ScoresActivity.class);
            startActivity(intent);
        });
    }
}