
package review;

import Enrollment.EnrollmentModels;
import Enrollment.EnrollmentService;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReviewService {

    private ArrayList<Review> reviews = new ArrayList<>();
    private EnrollmentService enrollmentService;

    private Map<String, Double> courseAverages = new HashMap<>();
    private Map<String, Boolean> flaggedCourses = new HashMap<>();

    public ReviewService(EnrollmentService enrollmentService) {
        if (enrollmentService == null) {
            throw new IllegalArgumentException(
                    "EnrollmentService cannot be null.");
        }
        this.enrollmentService = enrollmentService;
    }

    // Add a review
    public String addReview(String enrollmentId,
                            int rating, String comment) {

        if (rating < 1 || rating > 5) {
            return "Rating must be between 1 and 5.";
        }

        EnrollmentModels.Enrollment enrollment =
                enrollmentService.getEnrollment(enrollmentId);

        if (enrollment == null) {
            return "Enrollment not found.";
        }

        if (enrollment.progressPercent < 25) {
            return "You must complete at least 25% of the course.";
        }

        for (Review review : reviews) {
            if (review.getStudentId().equals(enrollment.studentId)
                    && review.getCourseId().equals(enrollment.courseId)) {
                return "You have already reviewed this course.";
            }
        }

        Review review = new Review(
                enrollment.studentId,
                enrollment.courseId,
                rating,
                comment);

        reviews.add(review);
        recalculateAverage(enrollment.courseId);

        return "Review added successfully.";
    }

    // Edit an existing review within 30 days
    public String editReview(String studentId,
                             String courseId,
                             int newRating,
                             String newComment) {

        if (newRating < 1 || newRating > 5) {
            return "Rating must be between 1 and 5.";
        }

        for (Review review : reviews) {

            if (review.getStudentId().equals(studentId)
                    && review.getCourseId().equals(courseId)) {

                long days = ChronoUnit.DAYS.between(
                        review.getReviewDate(), LocalDateTime.now());

                if (days > 30) {
                    return "Review editing period has expired.";
                }

                review.updateReview(newRating, newComment);
                recalculateAverage(courseId);

                return "Review updated successfully.";
            }
        }

        return "Review not found.";
    }

    // Recalculate average after every change
    private void recalculateAverage(String courseId) {

        int total = 0;
        int count = 0;

        for (Review review : reviews) {
            if (review.getCourseId().equals(courseId)) {
                total += review.getRating();
                count++;
            }
        }

        double average = count == 0 ? 0.0 : (double) total / count;
        courseAverages.put(courseId, average);

        flaggedCourses.put(courseId,
                count >= 20 && average < 2.5);
    }

    public double getAverageRating(String courseId) {
        return courseAverages.getOrDefault(courseId, 0.0);
    }

    public boolean isCourseFlagged(String courseId) {
        return flaggedCourses.getOrDefault(courseId, false);
    }

    public int getReviewCount(String courseId) {
        int count = 0;

        for (Review review : reviews) {
            if (review.getCourseId().equals(courseId)) {
                count++;
            }
        }

        return count;
    }

    public ArrayList<Review> getAllReviews() {
        return new ArrayList<>(reviews);
    }

    public void displayReviews(String courseId) {

        System.out.println("\n===== COURSE REVIEWS =====");

        for (Review review : reviews) {
            if (review.getCourseId().equals(courseId)) {
                System.out.println("Student: "
                        + review.getStudentId());
                System.out.println("Rating: "
                        + review.getRating());
                System.out.println("Comment: "
                        + review.getComment());
                System.out.println("--------------------");
            }
        }

        System.out.println("Average rating: "
                + getAverageRating(courseId));

        if (isCourseFlagged(courseId)) {
            System.out.println(
                    "WARNING: Course flagged for admin review.");
        }
    }
}
