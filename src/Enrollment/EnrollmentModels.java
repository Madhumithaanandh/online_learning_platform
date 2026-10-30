package Enrollment;

import java.time.LocalDateTime;

public class EnrollmentModels {

    public enum UserRole {
        STUDENT,
        INSTRUCTOR,
        ADMIN
    }

    public enum CourseStatus {
        DRAFT,
        PUBLISHED,
        WITHDRAWN
    }

    public static class User {

        public String userId;
        public String name;
        public UserRole role;

        public User(String userId, String name, UserRole role) {
            this.userId = userId;
            this.name = name;
            this.role = role;
        }
    }

    public static class Course {

        public String courseId;
        public String title;
        public String instructorId;
        public double price;
        public CourseStatus status;

        public Course(String courseId, String title,
                      String instructorId, double price,
                      CourseStatus status) {

            this.courseId = courseId;
            this.title = title;
            this.instructorId = instructorId;
            this.price = price;
            this.status = status;
        }
    }

    public static class Enrollment {

        public String enrollmentId;
        public String studentId;
        public String courseId;
        public EnrollmentStatus status;
        public double progressPercent = 0.0;
        public LocalDateTime enrollmentDate;
        public LocalDateTime expiryDate;

        public Enrollment(String enrollmentId,
                          String studentId,
                          String courseId,
                          EnrollmentStatus status) {

            this.enrollmentId = enrollmentId;
            this.studentId = studentId;
            this.courseId = courseId;
            this.status = status;

            this.enrollmentDate = LocalDateTime.now();

            if (status == EnrollmentStatus.PENDING_PAYMENT) {
                this.expiryDate = enrollmentDate.plusHours(24);
            }
        }
    }
}