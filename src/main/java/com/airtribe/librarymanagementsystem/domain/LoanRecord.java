package com.airtribe.librarymanagementsystem.domain;

import java.time.LocalDate;
import java.util.Objects;

public class LoanRecord {
    private final String loanId;
    private final String bookId;
    private final String patronId;
    private final LocalDate checkoutDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;

    public LoanRecord(String loanId, String bookId, String patronId, LocalDate checkoutDate, LocalDate dueDate) {
        this.loanId = Objects.requireNonNull(loanId, "loanId cannot be null");
        this.bookId = Objects.requireNonNull(bookId, "bookId cannot be null");
        this.patronId = Objects.requireNonNull(patronId, "patronId cannot be null");
        this.checkoutDate = Objects.requireNonNull(checkoutDate, "checkoutDate cannot be null");
        this.dueDate = Objects.requireNonNull(dueDate, "dueDate cannot be null");
    }

    public String getLoanId() {
        return loanId;
    }

    public String getBookId() {
        return bookId;
    }

    public String getPatronId() {
        return patronId;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public boolean isReturned() {
        return returnDate != null;
    }

    public void markReturned(LocalDate returnDate) {
        this.returnDate = Objects.requireNonNull(returnDate, "returnDate cannot be null");
    }
}
