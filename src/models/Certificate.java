package models;

import java.time.LocalDateTime;

public class Certificate {

    private String certificateId;
    private String studentId;
    private String courseId;
    private LocalDateTime issuedAt;

    public Certificate(String certificateId, String studentId, String courseId) {
        this.certificateId = certificateId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.issuedAt = LocalDateTime.now();
    }

    public String getCertificateId() {
        return certificateId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }
}