package com.airtribe.librarymanagementsystem.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Reservation {
    private final String reservationId;
    private final String patronId;
    private final String isbn;
    private final String branchId;
    private final LocalDateTime createdAt;
    private ReservationStatus status;

    public Reservation(String reservationId, String patronId, String isbn, String branchId) {
        this.reservationId = Objects.requireNonNull(reservationId, "reservationId cannot be null");
        this.patronId = Objects.requireNonNull(patronId, "patronId cannot be null");
        this.isbn = Objects.requireNonNull(isbn, "isbn cannot be null");
        this.branchId = Objects.requireNonNull(branchId, "branchId cannot be null");
        this.createdAt = LocalDateTime.now();
        this.status = ReservationStatus.WAITING;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getPatronId() {
        return patronId;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getBranchId() {
        return branchId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = Objects.requireNonNull(status, "status cannot be null");
    }
}
