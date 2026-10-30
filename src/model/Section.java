package model;

import java.util.ArrayList;

public class Section {

    private int sectionId;
    private String sectionTitle;
    private ArrayList<Lesson> lessons;

    public Section(int sectionId, String sectionTitle) {
        this.sectionId = sectionId;
        this.sectionTitle = sectionTitle;
        this.lessons = new ArrayList<>();
    }

    public int getSectionId() {
        return sectionId;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    public int getLessonCount() {
        return lessons.size();
    }

    public void displaySection() {
        System.out.println("Section ID: " + sectionId);
        System.out.println("Section Title: " + sectionTitle);
        System.out.println("Number of Lessons: " + lessons.size());

        for (Lesson lesson : lessons) {
            lesson.displayLesson();
        }
    }
}