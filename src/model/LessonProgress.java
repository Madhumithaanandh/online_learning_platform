package model;

public class LessonProgress {

    private int studentId;
    private int lessonId;
    private boolean completed;
    private double watchPercentage;

    public LessonProgress(int studentId, int lessonId) {
        this.studentId = studentId;
        this.lessonId = lessonId;
        this.completed = false;
        this.watchPercentage = 0.0;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getLessonId() {
        return lessonId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public double getWatchPercentage() {
        return watchPercentage;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setWatchPercentage(double watchPercentage) {
        if (watchPercentage >= 0 && watchPercentage <= 100) {
            this.watchPercentage = watchPercentage;
        } else {
            System.out.println("Invalid watch percentage!");
        }
    }

    public void updateVideoProgress(double watchPercentage) {

        if (watchPercentage < 0 || watchPercentage > 100) {
            System.out.println("Invalid watch percentage!");
            return;
        }

        this.watchPercentage = watchPercentage;

        if (watchPercentage >= 90) {
            this.completed = true;
            System.out.println("Video lesson completed!");
        } else {
            this.completed = false;
            System.out.println("Watch at least 90% to complete the lesson.");
        }
    }
}