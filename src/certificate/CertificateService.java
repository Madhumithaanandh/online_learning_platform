package certificate;

import Enrollment.EnrollmentModels;
import Enrollment.EnrollmentService;
import Enrollment.EnrollmentStatus;
import models.Certificate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CertificateService {

    private EnrollmentService enrollmentService;

    // Stores certificates using enrollment ID
    private Map<String, Certificate> certificatesByEnrollment = new HashMap<>();

    public CertificateService(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    // Issue certificate only after course completion
    public String issueCertificate(String enrollmentId) {

        EnrollmentModels.Enrollment enrollment =
                enrollmentService.getEnrollment(enrollmentId);

        if (enrollment == null) {
            return "Enrollment not found.";
        }

        if (enrollment.status != EnrollmentStatus.COMPLETED) {
            return "Certificate can be issued only after course completion.";
        }

        // Prevent duplicate certificate issuance
        if (certificatesByEnrollment.containsKey(enrollmentId)) {
            Certificate existing = certificatesByEnrollment.get(enrollmentId);
            return "Certificate already issued. Certificate ID: "
                    + existing.getCertificateId();
        }

        String certificateId = "CERT-" + UUID.randomUUID();

        Certificate certificate = new Certificate(
                certificateId,
                enrollment.studentId,
                enrollment.courseId
        );

        certificatesByEnrollment.put(enrollmentId, certificate);

        return "Certificate issued successfully. Certificate ID: "
                + certificateId;
    }

    // Get certificate for an enrollment
    public Certificate getCertificate(String enrollmentId) {
        return certificatesByEnrollment.get(enrollmentId);
    }

    // Get all issued certificates
    public ArrayList<Certificate> getAllCertificates() {
        return new ArrayList<>(certificatesByEnrollment.values());
    }

    // Display all certificates
    public void displayCertificates() {

        if (certificatesByEnrollment.isEmpty()) {
            System.out.println("No certificates issued yet.");
            return;
        }

        for (Certificate certificate : certificatesByEnrollment.values()) {
            System.out.println("Certificate ID: "
                    + certificate.getCertificateId());
            System.out.println("Student ID: "
                    + certificate.getStudentId());
            System.out.println("Course ID: "
                    + certificate.getCourseId());
            System.out.println("Issued At: "
                    + certificate.getIssuedAt());
            System.out.println("-------------------------");
        }
    }
}