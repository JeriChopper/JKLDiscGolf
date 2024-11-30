package org.gpiste.mobileapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class RoundActivity extends AppCompatActivity {

    private TextView holeDetails;
    private EditText scoreInput;

    private ArrayList<HashMap<String, String>> holes;
    private int currentHoleIndex = 0;
    private int[] scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);

        holeDetails = findViewById(R.id.holeDetails);
        scoreInput = findViewById(R.id.scoreInput);
        Button nextButton = findViewById(R.id.nextButton);
        Button prevButton = findViewById(R.id.prevButton);
        Button saveScoreButton = findViewById(R.id.saveScoreButton);
        Button finishRoundButton = findViewById(R.id.finishRoundButton);

        // Load course data
        String courseName = getIntent().getStringExtra("courseName");
        loadCourseData(courseName);

        // Initialize scores array
        scores = new int[holes.size()];

        // Display the first hole
        displayHole();

        // Navigate to the next hole
        nextButton.setOnClickListener(v -> {
            if (currentHoleIndex < holes.size() - 1) {
                currentHoleIndex++;
                displayHole();
            } else {
                Toast.makeText(this, "This is the last hole.", Toast.LENGTH_SHORT).show();
            }
        });

        // Navigate to the previous hole
        prevButton.setOnClickListener(v -> {
            if (currentHoleIndex > 0) {
                currentHoleIndex--;
                displayHole();
            } else {
                Toast.makeText(this, "This is the first hole.", Toast.LENGTH_SHORT).show();
            }
        });

        // Save score for the current hole
        saveScoreButton.setOnClickListener(v -> {
            saveCurrentHoleScore();
        });

        // Finish round and validate scores
        finishRoundButton.setOnClickListener(v -> {
            if (areAllScoresEntered()) {
                saveRoundResults();
                finish();
            } else {
                Toast.makeText(this, "Please complete all hole scores before finishing the round.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Load data from CourseData.json and parse details
    private void loadCourseData(String courseName) {
        try {
            InputStream inputStream = getAssets().open("CourseData.json");
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();

            String json = new String(buffer, StandardCharsets.UTF_8);
            JSONObject jsonObject = new JSONObject(json);
            JSONArray coursesArray = jsonObject.getJSONArray("courses");

            for (int i = 0; i < coursesArray.length(); i++) {
                JSONObject course = coursesArray.getJSONObject(i);
                if (course.getString("name").equals(courseName)) {
                    JSONArray holesArray = course.getJSONArray("holes");
                    holes = new ArrayList<>();
                    for (int j = 0; j < holesArray.length(); j++) {
                        JSONObject hole = holesArray.getJSONObject(j);
                        HashMap<String, String> holeDetails = new HashMap<>();
                        holeDetails.put("hole", hole.getString("hole"));
                        holeDetails.put("dist", hole.getString("dist"));
                        holeDetails.put("par", hole.getString("par"));
                        holes.add(holeDetails);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to load course data.", Toast.LENGTH_SHORT).show();
        }
    }

    // Display hole which is played and the details of the hole
    private void displayHole() {
        if (holes != null && currentHoleIndex >= 0 && currentHoleIndex < holes.size()) {
            HashMap<String, String> hole = holes.get(currentHoleIndex);
            String holeInfo = "Hole " + hole.get("hole") + "\n" +
                    "Distance: " + hole.get("dist") + "m\n" +
                    "Par: " + hole.get("par");
            holeDetails.setText(holeInfo);

            // Pre-fill score if already entered
            int currentScore = scores[currentHoleIndex];
            scoreInput.setText(currentScore == 0 ? "" : String.valueOf(currentScore));
        }
    }

    // Method to save the current hole score
    private void saveCurrentHoleScore() {
        try {
            String input = scoreInput.getText().toString();
            if (!input.isEmpty()) {
                int score = Integer.parseInt(input);
                scores[currentHoleIndex] = score;
                Toast.makeText(this, "Score saved for hole " + (currentHoleIndex + 1), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Enter a score for this hole.", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid score.", Toast.LENGTH_SHORT).show();
        }
    }

    // Check if all scores are entered for the entire layout. If not return false
    private boolean areAllScoresEntered() {
        for (int score : scores) {
            if (score == 0) {
                return false;
            }
        }
        return true;
    }

    // Save the entire round results and calculate the score related to par.
    private void saveRoundResults() {
        int totalScore = 0;
        int totalPar = 0;

        for (int i = 0; i < holes.size(); i++) {
            int score = scores[i];
            int par = Integer.parseInt(holes.get(i).get("par"));
            totalScore += score;
            totalPar += par;
        }

        int differential = totalScore - totalPar;

        String courseName = getIntent().getStringExtra("courseName");
        storeRoundData(courseName, totalPar, totalScore, differential);

        Toast.makeText(this, "Round saved! Score: " + differential, Toast.LENGTH_SHORT).show();
    }

    // Store round data for further use (look scores you have played in score section etc.)
    private void storeRoundData(String courseName, int totalPar, int totalScore, int differential) {
        String roundData = courseName + " - Total Par: " + totalPar + ", Total Score: " + totalScore + ", Differential: " + differential;

        SharedPreferences preferences = getSharedPreferences("RoundScores", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        ArrayList<String> rounds = new ArrayList<>(preferences.getStringSet("scores", new HashSet<>()));
        rounds.add(roundData);

        editor.putStringSet("scores", new HashSet<>(rounds));
        editor.apply();
    }
}
