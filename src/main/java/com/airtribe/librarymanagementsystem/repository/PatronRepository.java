package com.airtribe.librarymanagementsystem.repository;

import com.airtribe.librarymanagementsystem.domain.Patron;
import java.util.List;
import java.util.Optional;

public interface PatronRepository {
    void save(Patron patron);
    Optional<Patron> findById(String patronId);
    List<Patron> findAll();
}
