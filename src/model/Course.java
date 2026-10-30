package model;

import java.util.ArrayList;

public class Course {

    private int courseId;
    private String title;
    private String description;
    private String category;
    private String level;
    private double price;
    private boolean published;

    private ArrayList<Section> sections;

    // Constructor
    public Course(int courseId, String title, String description,
                 String category, String level, double price) {

        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.level = level;
        this.price = price;
        this.published = false;

        sections = new ArrayList<>();
    }

    // Getters
    public int getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getLevel() {
        return level;
    }

    public double getPrice() {
        return price;
    }

    public boolean isPublished() {
        return published;
    }

    public ArrayList<Section> getSections() {
        return sections;
    }

    // Update Title
    public void setTitle(String title) {
        this.title = title;
    }

    // Update Description
    public void setDescription(String description) {
        this.description = description;
    }

    // Update Price
    public void setPrice(double price) {
        this.price = price;
    }

    // Add Section
    public void addSection(Section section) {
        sections.add(section);
        System.out.println("Section added successfully!");
    }

    // Get Section Count
    public int getSectionCount() {
        return sections.size();
    }

    // Get Total Lesson Count
    public int getTotalLessonCount() {

        int totalLessons = 0;

        for (Section section : sections) {
            totalLessons += section.getLessonCount();
        }

        return totalLessons;
    }

    // Publish Course
    public void publishCourse() {
        published = true;
    }

    // Display Course Details
    public void displayCourse() {

        System.out.println("Course ID: " + courseId);
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Category: " + category);
        System.out.println("Level: " + level);
        System.out.println("Price: " + price);
        System.out.println("Published: " + published);

        System.out.println("Number of Sections: " + sections.size());
        System.out.println("Total Lessons: " + getTotalLessonCount());

        for (Section section : sections) {
            section.displaySection();
        }
    }
}