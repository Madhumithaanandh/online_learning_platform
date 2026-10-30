package model;

public class Lesson {

    private int lessonId;
    private String lessonTitle;
    private String lessonType;
    private int duration;
    private int lessonOrder;

    // Constructor
    public Lesson(int lessonId, String lessonTitle,
                 String lessonType, int duration, int lessonOrder) {

        this.lessonId = lessonId;
        this.lessonTitle = lessonTitle;
        this.lessonType = lessonType;
        this.duration = duration;
        this.lessonOrder = lessonOrder;
    }

    // Getters
    public int getLessonId() {
        return lessonId;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public String getLessonType() {
        return lessonType;
    }

    public int getDuration() {
        return duration;
    }

    public int getLessonOrder() {
        return lessonOrder;
    }

    // Update Lesson Title
    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }

    // Update Lesson Type
    public void setLessonType(String lessonType) {
        this.lessonType = lessonType;
    }

    // Update Duration
    public void setDuration(int duration) {
        this.duration = duration;
    }

    // Update Lesson Order
    public void setLessonOrder(int lessonOrder) {
        this.lessonOrder = lessonOrder;
    }

    // Display Lesson Details
    public void displayLesson() {

        System.out.println("Lesson ID: " + lessonId);
        System.out.println("Lesson Title: " + lessonTitle);
        System.out.println("Lesson Type: " + lessonType);
        System.out.println("Duration: " + duration + " minutes");
        System.out.println("Lesson Order: " + lessonOrder);
    }
}