
package payment;

import models.Payment;
import Enrollment.EnrollmentService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PaymentService {

    private ArrayList<Payment> allPayments = new ArrayList<>();

    private Map<String, Payment> latestPayments = new HashMap<>();

    private Map<String, LocalDateTime> firstFailedAt = new HashMap<>();

    private EnrollmentService enrollmentService;

    // Constructor
    public PaymentService(EnrollmentService enrollmentService) {
        if (enrollmentService == null) {
            throw new IllegalArgumentException(
                    "EnrollmentService cannot be null.");
        }

        this.enrollmentService = enrollmentService;
    }

    // Process a new payment
    public Payment processPayment(
            String enrollmentId,
            double amount,
            PaymentMethod method,
            PaymentStatus status) {

        if (enrollmentId == null || enrollmentId.isBlank()
                || amount <= 0 || method == null || status == null) {
            throw new IllegalArgumentException("Invalid payment details.");
        }

        if (latestPayments.containsKey(enrollmentId)) {
            throw new IllegalArgumentException(
                    "Payment already exists. Use retry for failed payments.");
        }

        if (status == PaymentStatus.REFUNDED) {
            throw new IllegalArgumentException(
                    "A new payment cannot have REFUNDED status.");
        }

        // Ask EnrollmentService to activate or retain pending status
        String result = enrollmentService.processPayment(
                enrollmentId, status == PaymentStatus.PAID);

        if (!result.startsWith("Payment successful!")
                && !result.equals("Payment failed. Please try again.")) {
            throw new IllegalStateException(result);
        }

        Payment payment = new Payment(amount, method, status);

        allPayments.add(payment);
        latestPayments.put(enrollmentId, payment);

        if (status == PaymentStatus.FAILED) {
            firstFailedAt.put(enrollmentId, payment.getPaymentDate());
        }

        return payment;
    }

    // Retry a failed payment within 24 hours
    public boolean retryPayment(
            String enrollmentId,
            PaymentMethod method,
            boolean success) {

        if (enrollmentId == null || method == null) {
            return false;
        }

        Payment previousPayment = latestPayments.get(enrollmentId);

        LocalDateTime failedAt = firstFailedAt.get(enrollmentId);

        if (previousPayment == null
                || previousPayment.getStatus() != PaymentStatus.FAILED
                || failedAt == null) {
            return false;
        }

        long hours = ChronoUnit.HOURS.between(
                failedAt, LocalDateTime.now());

        if (hours >= 24) {
            return false;
        }

        // EnrollmentService must support retrying an existing
        // PENDING_PAYMENT enrollment.
        String result = enrollmentService.processPayment(
                enrollmentId, success);

        if (!success || !result.startsWith("Payment successful!")) {
            return false;
        }

        Payment retryPayment = new Payment(
                previousPayment.getAmount(),
                method,
                PaymentStatus.PAID);

        allPayments.add(retryPayment);
        latestPayments.put(enrollmentId, retryPayment);

        firstFailedAt.remove(enrollmentId);

        return true;
    }

    // Get all payment records
    public ArrayList<Payment> getAllPayments() {
        return new ArrayList<>(allPayments);
    }

    // Get payment for a particular enrollment
    public Payment getPayment(String enrollmentId) {
        return latestPayments.get(enrollmentId);
    }
}
