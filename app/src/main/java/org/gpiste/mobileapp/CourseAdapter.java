package org.gpiste.mobileapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private final List<Course> courseList;

    public CourseAdapter(List<Course> courseList) {
        this.courseList = courseList;
    }

    // Create a new view to Recycler view
    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(view);
    }

    // Bind data from Course object
    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courseList.get(position);
        holder.courseName.setText(course.getName());
        holder.courseDetails.setText(new StringBuilder().append("Holes: ").append(course.getHoleCount()).append(", Avg. Distance: ").append(String.format("%.1f", course.getAverageDistance())).append(" m").toString());
        holder.courseDescription.setText(course.getDescription());
    }


    @Override
    public int getItemCount() {
        return courseList.size();
    }

    static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView courseName;
        TextView courseDetails;
        TextView courseDescription;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.courseName);
            courseDetails = itemView.findViewById(R.id.courseDetails);
            courseDescription = itemView.findViewById(R.id.courseDescription);
        }
    }
}
