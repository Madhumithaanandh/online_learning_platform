package model;

import java.time.LocalDateTime;

public class QuizAttempt {

    private int studentId;
    private int lessonId;
    private int attemptNumber;
    private double score;
    private boolean passed;
    private LocalDateTime attemptDate;

    public QuizAttempt(int studentId, int lessonId,
                       int attemptNumber, double score) {

        this.studentId = studentId;
        this.lessonId = lessonId;
        this.attemptNumber = attemptNumber;
        this.score = score;
        this.attemptDate = LocalDateTime.now();

        this.passed = score >= 60;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getLessonId() {
        return lessonId;
    }

    public int getAttemptNumber() {
        return attemptNumber;
    }

    public double getScore() {
        return score;
    }

    public boolean isPassed() {
        return passed;
    }

    public LocalDateTime getAttemptDate() {
        return attemptDate;
    }
}