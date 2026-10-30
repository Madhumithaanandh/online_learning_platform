package payment;

/**
 * PaymentStatus defines the possible states a payment can be in.
 *
 * PAID      - Payment was successful. Enrollment becomes active.
 * FAILED    - Payment attempt failed. Enrollment stays inactive.
 * REFUNDED  - A refund was issued. Enrollment is cancelled.
 */
public enum PaymentStatus {

    PAID,     // Money was successfully collected
    FAILED,   // Payment attempt did not go through
    REFUNDED  // Money was returned to the student
}
