package com.airtribe.librarymanagementsystem.repository;

import com.airtribe.librarymanagementsystem.domain.Reservation;
import java.util.List;

public interface ReservationRepository {
    void save(Reservation reservation);
    List<Reservation> findAll();
}
