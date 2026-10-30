package service;

import model.LessonProgress;
import model.QuizAttempt;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProgressService {

    private List<QuizAttempt> quizAttempts = new ArrayList<>();
    private List<LessonProgress> lessonProgressList = new ArrayList<>();
    private List<Integer> lessonOrder = new ArrayList<>();

    // Set lesson order
    public void setLessonOrder(List<Integer> lessonIds) {
        lessonOrder = new ArrayList<>(lessonIds);
    }

    // Find or create lesson progress
    private LessonProgress getLessonProgress(int studentId, int lessonId) {

        for (LessonProgress progress : lessonProgressList) {

            if (progress.getStudentId() == studentId &&
                progress.getLessonId() == lessonId) {

                return progress;
            }
        }

        LessonProgress progress =
                new LessonProgress(studentId, lessonId);

        lessonProgressList.add(progress);

        return progress;
    }

    // Check whether student can start the lesson
    public boolean canStartLesson(int studentId, int lessonId) {

        int currentIndex = lessonOrder.indexOf(lessonId);

        if (currentIndex == -1) {
            System.out.println("Lesson not found!");
            return false;
        }

        if (currentIndex == 0) {
            return true;
        }

        int previousLessonId = lessonOrder.get(currentIndex - 1);

        LessonProgress previousProgress =
                getLessonProgress(studentId, previousLessonId);

        if (!previousProgress.isCompleted()) {
            System.out.println("Complete the previous lesson first!");
            return false;
        }

        return true;
    }

    // Update video progress
    public void updateVideoProgress(
            int studentId, int lessonId, double percentage) {

        if (!canStartLesson(studentId, lessonId)) {
            return;
        }

        LessonProgress progress =
                getLessonProgress(studentId, lessonId);

        progress.updateVideoProgress(percentage);
    }

    // Mark reading lesson as completed
    public void completeReadingLesson(int studentId, int lessonId) {

        if (!canStartLesson(studentId, lessonId)) {
            return;
        }

        LessonProgress progress =
                getLessonProgress(studentId, lessonId);

        progress.setCompleted(true);

        System.out.println("Reading lesson completed!");
    }

    // Check quiz attempt permission
    public boolean canAttemptQuiz(int studentId, int lessonId) {

        if (!canStartLesson(studentId, lessonId)) {
            return false;
        }

        int failedAttempts = 0;
        LocalDateTime lastFailedAttempt = null;

        for (QuizAttempt attempt : quizAttempts) {

            if (attempt.getStudentId() == studentId &&
                attempt.getLessonId() == lessonId) {

                if (attempt.isPassed()) {
                    return false;
                }

                failedAttempts++;
                lastFailedAttempt = attempt.getAttemptDate();
            }
        }

        if (failedAttempts >= 3) {

            LocalDateTime allowedTime =
                    lastFailedAttempt.plusHours(24);

            if (LocalDateTime.now().isBefore(allowedTime)) {
                System.out.println(
                    "Please wait 24 hours before trying again."
                );
                return false;
            }
        }

        return true;
    }

    // Submit quiz result
    public void submitQuiz(int studentId, int lessonId, double score) {

        if (score < 0 || score > 100) {
            System.out.println("Invalid quiz score!");
            return;
        }

        if (!canAttemptQuiz(studentId, lessonId)) {
            return;
        }

        int attemptNumber = 1;

        for (QuizAttempt attempt : quizAttempts) {

            if (attempt.getStudentId() == studentId &&
                attempt.getLessonId() == lessonId) {

                attemptNumber++;
            }
        }

        QuizAttempt newAttempt =
                new QuizAttempt(studentId, lessonId, attemptNumber, score);

        quizAttempts.add(newAttempt);

        if (newAttempt.isPassed()) {

            LessonProgress progress =
                    getLessonProgress(studentId, lessonId);

            progress.setCompleted(true);

            System.out.println("Quiz passed! Lesson completed!");

        } else {
            System.out.println("Quiz failed!");
        }
    }

    // Calculate progress percentage
    public double calculateProgress(int studentId) {

        if (lessonOrder.isEmpty()) {
            return 0.0;
        }

        int completedLessons = 0;

        for (int lessonId : lessonOrder) {

            LessonProgress progress =
                    getLessonProgress(studentId, lessonId);

            if (progress.isCompleted()) {
                completedLessons++;
            }
        }

        return (completedLessons * 100.0) / lessonOrder.size();
    }

    // Display progress
    public void displayProgress(int studentId) {

        double percentage = calculateProgress(studentId);

        System.out.println("Course Progress: " + percentage + "%");

        if (percentage == 100.0) {
            System.out.println("Course completed!");
        }
    }
}