
package review;

import java.time.LocalDateTime;

public class Review {

    private String studentId;
    private String courseId;
    private int rating;
    private String comment;
    private LocalDateTime reviewDate;

    public Review(String studentId, String courseId,
                  int rating, String comment) {

        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException(
                    "Rating must be between 1 and 5.");
        }

        this.studentId = studentId;
        this.courseId = courseId;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = LocalDateTime.now();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public void setRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException(
                    "Rating must be between 1 and 5.");
        }
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void updateReview(int rating, String comment) {
        setRating(rating);
        setComment(comment);
    }
}
