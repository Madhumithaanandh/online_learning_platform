
package refund;

import Enrollment.EnrollmentModels;
import Enrollment.EnrollmentService;
import Enrollment.EnrollmentStatus;
import models.Payment;
import payment.PaymentService;
import payment.PaymentStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class RefundService {

    private PaymentService paymentService;
    private EnrollmentService enrollmentService;

    private Map<String, Double> instructorEarnings = new HashMap<>();
    private Map<String, Double> platformRevenue = new HashMap<>();

    // Prevent duplicate instructor credits
    private Map<String, Boolean> creditedEnrollments = new HashMap<>();

    public RefundService(PaymentService paymentService,
                         EnrollmentService enrollmentService) {

        if (paymentService == null || enrollmentService == null) {
            throw new IllegalArgumentException("Services cannot be null.");
        }

        this.paymentService = paymentService;
        this.enrollmentService = enrollmentService;
    }

    // Student requests a refund
    public String requestRefund(String enrollmentId) {

        EnrollmentModels.Enrollment enrollment =
                enrollmentService.getEnrollment(enrollmentId);

        if (enrollment == null) {
            return "Enrollment not found.";
        }

        Payment payment = paymentService.getPayment(enrollmentId);

        if (payment == null || payment.getStatus() != PaymentStatus.PAID) {
            return "No successful payment found.";
        }

        if (enrollment.status != EnrollmentStatus.ACTIVE) {
            return "Only active enrollments can request a refund.";
        }

        if (enrollment.progressPercent >= 20) {
            return "Refund denied. Course progress must be below 20%.";
        }

        LocalDateTime deadline = payment.getPaymentDate().plusDays(7);

        if (LocalDateTime.now().isAfter(deadline)) {
            return "Refund denied. The 7-day refund period has expired.";
        }

        payment.setStatus(PaymentStatus.REFUNDED);
        enrollment.status = EnrollmentStatus.CANCELLED;

        return "Full refund processed successfully.";
    }

    // Admin withdraws a course and refunds all paid enrollments
    public String adminWithdrawCourse(
            String courseId,
            ArrayList<EnrollmentModels.Course> courses) {

        boolean courseFound = false;

        for (EnrollmentModels.Course course : courses) {
            if (course.courseId.equals(courseId)) {
                course.status = EnrollmentModels.CourseStatus.WITHDRAWN;
                courseFound = true;
                break;
            }
        }

        if (!courseFound) {
            return "Course not found.";
        }

        int refundCount = 0;

        for (EnrollmentModels.Enrollment enrollment :
                enrollmentService.getAllEnrollments()) {

            if (enrollment.courseId.equals(courseId)) {

                Payment payment =
                        paymentService.getPayment(enrollment.enrollmentId);

                if (payment != null
                        && payment.getStatus() == PaymentStatus.PAID) {

                    payment.setStatus(PaymentStatus.REFUNDED);
                    enrollment.status = EnrollmentStatus.CANCELLED;
                    refundCount++;
                }
            }
        }

        return "Course withdrawn. Full refunds processed: " + refundCount;
    }

    // Credit instructor after the 7-day refund period
    public String creditInstructor(String enrollmentId,
                                   String instructorId) {

        EnrollmentModels.Enrollment enrollment =
                enrollmentService.getEnrollment(enrollmentId);

        if (enrollment == null) {
            return "Enrollment not found.";
        }

        Payment payment = paymentService.getPayment(enrollmentId);

        if (payment == null || payment.getStatus() != PaymentStatus.PAID) {
            return "No eligible paid payment found.";
        }

        if (creditedEnrollments.containsKey(enrollmentId)) {
            return "Instructor earnings already credited.";
        }

        LocalDateTime creditDate =
                payment.getPaymentDate().plusDays(7);

        if (LocalDateTime.now().isBefore(creditDate)) {
            return "Earnings will be credited after the 7-day refund period.";
        }

        double amount = payment.getAmount();
        double instructorShare = amount * 0.70;
        double platformShare = amount * 0.30;

        instructorEarnings.put(
                instructorId,
                instructorEarnings.getOrDefault(instructorId, 0.0)
                        + instructorShare);

        platformRevenue.put(
                instructorId,
                platformRevenue.getOrDefault(instructorId, 0.0)
                        + platformShare);

        creditedEnrollments.put(enrollmentId, true);

        return "Instructor credited: Rs. " + instructorShare
                + "\nPlatform revenue: Rs. " + platformShare;
    }

    public double getInstructorEarnings(String instructorId) {
        return instructorEarnings.getOrDefault(instructorId, 0.0);
    }

    public double getPlatformRevenue(String instructorId) {
        return platformRevenue.getOrDefault(instructorId, 0.0);
    }
}
