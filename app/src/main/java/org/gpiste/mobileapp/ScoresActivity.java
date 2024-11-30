package org.gpiste.mobileapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Set;

public class ScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        ListView scoresListView = findViewById(R.id.scoresListView);

        // Load scores from SharedPreferences
        SharedPreferences preferences = getSharedPreferences("RoundScores", MODE_PRIVATE);
        Set<String> scoresSet = preferences.getStringSet("scores", null);

        ArrayList<String> scoresList = new ArrayList<>();
        if (scoresSet != null) {
            scoresList.addAll(scoresSet);
        } else {
            scoresList.add("No scores recorded yet.");
        }

        // Populate ListView with scores
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, scoresList);
        scoresListView.setAdapter(adapter);
    }
}
