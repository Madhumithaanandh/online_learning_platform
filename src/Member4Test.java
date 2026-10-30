import Enrollment.EnrollmentModels;
import Enrollment.EnrollmentService;
import Enrollment.EnrollmentStatus;
import payment.PaymentService;
import payment.PaymentMethod;
import payment.PaymentStatus;
import refund.RefundService;
import review.ReviewService;
import certificate.CertificateService;

public class Member4Test {

    public static void main(String[] args) {

        EnrollmentService enrollmentService = new EnrollmentService();
        PaymentService paymentService = new PaymentService(enrollmentService);
        RefundService refundService =
                new RefundService(paymentService, enrollmentService);
        ReviewService reviewService = new ReviewService(enrollmentService);
        CertificateService certificateService =
                new CertificateService(enrollmentService);

        EnrollmentModels.User student =
                new EnrollmentModels.User(
                        "S101", "Student One",
                        EnrollmentModels.UserRole.STUDENT);

        EnrollmentModels.Course course =
                new EnrollmentModels.Course(
                        "C101", "Java Programming", "I101", 1000,
                        EnrollmentModels.CourseStatus.PUBLISHED);

        // PAYMENT TEST
        System.out.println("\n--- PAYMENT TEST ---");

        String result = enrollmentService.enrollStudent(student, course);
        System.out.println(result);

        String enrollmentId = "E1";

        paymentService.processPayment(
                enrollmentId, 1000,
                PaymentMethod.UPI, PaymentStatus.PAID);

        System.out.println("Payment status: "
                + paymentService.getPayment(enrollmentId).getStatus());

        // REFUND TEST
        System.out.println("\n--- REFUND TEST ---");

        System.out.println(refundService.requestRefund(enrollmentId));

        // REVIEW TEST
        System.out.println("\n--- REVIEW TEST ---");

        EnrollmentModels.Enrollment enrollment =
                enrollmentService.getEnrollment(enrollmentId);

        enrollment.progressPercent = 30;

        System.out.println(
                reviewService.addReview(enrollmentId, 5, "Excellent course"));

        System.out.println("Average rating: "
                + reviewService.getAverageRating("C101"));

        // CERTIFICATE TEST
        System.out.println("\n--- CERTIFICATE TEST ---");

        enrollment.status = EnrollmentStatus.COMPLETED;

        System.out.println(
                certificateService.issueCertificate(enrollmentId));

        System.out.println(
                certificateService.issueCertificate(enrollmentId));
    }
}