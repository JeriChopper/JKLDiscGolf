package org.gpiste.mobileapp;

// Course object to represent the disc golf course

public class Course {
    private final String name;
    private final int holeCount;
    private final double averageDistance;
    private final String description;

    public Course(String name, String description, int holeCount, double averageDistance) {
        this.name = name;
        this.description = description;
        this.holeCount = holeCount;
        this.averageDistance = averageDistance;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getHoleCount() {
        return holeCount;
    }

    public double getAverageDistance() {
        return averageDistance;
    }
}