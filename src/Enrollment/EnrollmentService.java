package Enrollment;

import java.util.ArrayList;
import java.time.LocalDateTime;

public class EnrollmentService {

    ArrayList<EnrollmentModels.Enrollment> enrollments =
            new ArrayList<>();

    int enrollmentCounter = 1;

    // Count active courses
    public int countActiveCourses(String studentId) {

        updateExpiredEnrollments();

        int count = 0;

        for (EnrollmentModels.Enrollment e : enrollments) {

            if (e.studentId.equals(studentId)
                    && e.status == EnrollmentStatus.ACTIVE) {
                count++;
            }
        }

        return count;
    }

    // Enroll student in a course
    public String enrollStudent(
            EnrollmentModels.User student,
            EnrollmentModels.Course course) {

        updateExpiredEnrollments();

        if (student.role != EnrollmentModels.UserRole.STUDENT) {
            return "Only students can enroll in courses.";
        }

        if (course.status != EnrollmentModels.CourseStatus.PUBLISHED) {
            return "This course is not available for enrollment.";
        }

        if (student.userId.equals(course.instructorId)) {
            return "Instructor cannot enroll in their own course.";
        }

        for (EnrollmentModels.Enrollment e : enrollments) {

            if (e.studentId.equals(student.userId)
                    && e.courseId.equals(course.courseId)) {

                return "Already enrolled in this course.";
            }
        }

        if (countActiveCourses(student.userId) >= 5) {
            return "You can enroll in a maximum of 5 active courses.";
        }

        String id = "E" + enrollmentCounter;
        enrollmentCounter++;

        EnrollmentStatus status;

        if (course.price == 0) {
            status = EnrollmentStatus.ACTIVE;
        } else {
            status = EnrollmentStatus.PENDING_PAYMENT;
        }

        EnrollmentModels.Enrollment enrollment =
                new EnrollmentModels.Enrollment(
                        id,
                        student.userId,
                        course.courseId,
                        status);

        enrollments.add(enrollment);

        if (status == EnrollmentStatus.ACTIVE) {
            return "Enrollment successful! Enrollment ID: " + id;
        } else {
            return "Payment required. Enrollment ID: " + id;
        }
    }

    // Process payment
    public String processPayment(
            String enrollmentId, boolean success) {

        updateExpiredEnrollments();

        for (EnrollmentModels.Enrollment e : enrollments) {

            if (e.enrollmentId.equals(enrollmentId)) {

                if (e.status == EnrollmentStatus.EXPIRED) {
                    return "Enrollment expired. Please enroll again.";
                }

                if (e.status != EnrollmentStatus.PENDING_PAYMENT) {
                    return "Payment is not pending for this enrollment.";
                }

                if (success) {

                    if (countActiveCourses(e.studentId) >= 5) {
                        return "Maximum active course limit reached.";
                    }

                    e.status = EnrollmentStatus.ACTIVE;

                    return "Payment successful! Enrollment activated.";
                } else {
                    return "Payment failed. Please try again.";
                }
            }
        }

        return "Enrollment ID not found.";
    }

    // Update expired pending enrollments
    public void updateExpiredEnrollments() {

        for (EnrollmentModels.Enrollment e : enrollments) {

            if (e.status == EnrollmentStatus.PENDING_PAYMENT
                    && LocalDateTime.now().isAfter(e.expiryDate)) {

                e.status = EnrollmentStatus.EXPIRED;
            }
        }
    }

    public EnrollmentModels.Enrollment getEnrollment(String enrollmentId) {
        for (EnrollmentModels.Enrollment e : enrollments) {
            if (e.enrollmentId.equals(enrollmentId)) return e;
        }
        return null;
    }

    public ArrayList<EnrollmentModels.Enrollment> getAllEnrollments() {
        return new ArrayList<>(enrollments);
    }

    // Display all enrollments
    public void displayEnrollments() {

        updateExpiredEnrollments();

        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found.");
            return;
        }

        System.out.println("\n===== ENROLLMENT DETAILS =====");

        for (EnrollmentModels.Enrollment e : enrollments) {

            System.out.println("Enrollment ID: " + e.enrollmentId);
            System.out.println("Student ID: " + e.studentId);
            System.out.println("Course ID: " + e.courseId);
            System.out.println("Status: " + e.status);
            System.out.println("-----------------------------");
        }
    }
}