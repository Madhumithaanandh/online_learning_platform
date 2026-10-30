package payment;

/**
 * PaymentMethod defines the allowed ways a student can pay for a course.
 *
 * Using an enum (instead of plain Strings) means:
 *  - Only UPI, CARD, or NET_BANKING are valid values.
 *  - Java will show a compile error if an invalid method is used.
 *  - No accidental typos like "upi" or "Upi".
 */
public enum PaymentMethod {

    UPI,         // Unified Payments Interface (e.g., Google Pay, PhonePe)
    CARD,        // Credit or Debit card
    NET_BANKING  // Internet banking through a bank's website
}
