package org.gpiste.mobileapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewCourses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Course> courseList = loadCoursesFromJson();
        CourseAdapter courseAdapter = new CourseAdapter(courseList);
        recyclerView.setAdapter(courseAdapter);
    }

    // Load course data from CourseData.json to preview the courses available.
    // Count average distance and show description of the course
    private List<Course> loadCoursesFromJson() {
        List<Course> courses = new ArrayList<>();
        try {
            // Load the JSON file
            InputStream inputStream = getAssets().open("CourseData.json");
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();

            // Parse JSON
            String json = new String(buffer, StandardCharsets.UTF_8);
            JSONObject jsonObject = new JSONObject(json);
            JSONArray coursesArray = jsonObject.getJSONArray("courses");

            for (int i = 0; i < coursesArray.length(); i++) {
                JSONObject courseObj = coursesArray.getJSONObject(i);
                String name = courseObj.getString("name");
                String description = courseObj.getString("description");
                JSONArray holesArray = courseObj.getJSONArray("holes");


                int totalDistance = 0;
                for (int j = 0; j < holesArray.length(); j++) {
                    JSONObject hole = holesArray.getJSONObject(j);
                    totalDistance += hole.getInt("dist");
                }
                double averageDistance = (double) totalDistance / holesArray.length();
                int holeCount = holesArray.length();

                courses.add(new Course(name, description, holeCount, averageDistance));
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return courses;
    }
}

