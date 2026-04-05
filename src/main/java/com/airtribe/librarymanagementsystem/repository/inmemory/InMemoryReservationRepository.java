package com.airtribe.librarymanagementsystem.repository.inmemory;

import com.airtribe.librarymanagementsystem.domain.Reservation;
import com.airtribe.librarymanagementsystem.repository.ReservationRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryReservationRepository implements ReservationRepository {
    private final List<Reservation> reservations = new CopyOnWriteArrayList<>();

    @Override
    public void save(Reservation reservation) {
        reservations.removeIf(existing -> existing.getReservationId().equals(reservation.getReservationId()));
        reservations.add(reservation);
    }

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(reservations);
    }
}
