package com.airtribe.librarymanagementsystem.service;

import com.airtribe.librarymanagementsystem.domain.Patron;
import java.util.List;

public interface PatronService {
    void addPatron(Patron patron);
    void updatePatron(String patronId, String name, String email, String phoneNumber);
    Patron findPatronById(String patronId);
    List<String> getBorrowingHistory(String patronId);
}
