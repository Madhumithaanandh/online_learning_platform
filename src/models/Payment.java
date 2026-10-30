package models;

import java.time.LocalDateTime;
import payment.PaymentMethod;
import payment.PaymentStatus;

/**
 * Payment stores all the details of a single payment transaction.
 *
 * ENHANCED from the original starter class:
 *  - paymentMethod now uses the PaymentMethod enum (was String)
 *  - status now uses the PaymentStatus enum (was String)
 *  - Added input validation in the constructor
 *  - Added a paymentDate setter for retry logic (24-hour window check)
 *
 * OOP Concept Used: Encapsulation — all fields are private.
 */
public class Payment {

    // How much the student paid (in rupees)
    private double amount;

    // The exact date and time the payment was made
    private LocalDateTime paymentDate;

    // Which method was used: UPI, CARD, or NET_BANKING
    private PaymentMethod paymentMethod;

    // Current state of the payment: PAID, FAILED, or REFUNDED
    private PaymentStatus status;

    // -----------------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------------

    /**
     * Creates a new Payment record.
     *
     * @param amount        The payment amount (must be greater than 0)
     * @param paymentMethod The method used (UPI, CARD, NET_BANKING)
     * @param status        The initial status (PAID, FAILED, REFUNDED)
     */
    public Payment(double amount, PaymentMethod paymentMethod, PaymentStatus status) {

        // Validate: amount must be a positive number
        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be greater than zero.");
        }
        if (paymentMethod == null) {
            throw new IllegalArgumentException("Payment method cannot be null.");
        }
        if (status == null) {
            throw new IllegalArgumentException("Payment status cannot be null.");
        }

        this.amount = amount;
        this.paymentDate = LocalDateTime.now(); // Record the current time
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    // -----------------------------------------------------------------------
    // Getters — read-only access to fields
    // -----------------------------------------------------------------------

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    // -----------------------------------------------------------------------
    // Setters — only specific fields can be changed after creation
    // -----------------------------------------------------------------------

    /**
     * Updates the payment status.
     * Used when a retry succeeds (FAILED → PAID) or a refund is issued (PAID → REFUNDED).
     */
    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    /**
     * Updates the payment date.
     * Useful in tests when we need to simulate a payment made in the past
     * (e.g., to test the 24-hour retry window or 7-day refund window).
     */
    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    // -----------------------------------------------------------------------
    // toString
    // -----------------------------------------------------------------------
    @Override
    public String toString() {
        return "Payment{amount=" + amount
                + ", method=" + paymentMethod
                + ", status=" + status
                + ", date=" + paymentDate + "}";
    }
}