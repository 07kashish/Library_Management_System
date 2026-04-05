package com.airtribe.librarymanagementsystem.service;

import com.airtribe.librarymanagementsystem.domain.Patron;
import com.airtribe.librarymanagementsystem.repository.PatronRepository;
import java.util.List;
import java.util.logging.Logger;

public class DefaultPatronService implements PatronService {
    private static final Logger LOGGER = Logger.getLogger(DefaultPatronService.class.getName());

    private final PatronRepository patronRepository;

    public DefaultPatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    @Override
    public void addPatron(Patron patron) {
        patronRepository.save(patron);
        LOGGER.info(() -> "Registered patron " + patron.getPatronId());
    }

    @Override
    public void updatePatron(String patronId, String name, String email, String phoneNumber) {
        Patron patron = findPatronById(patronId);
        patron.setName(name);
        patron.setEmail(email);
        patron.setPhoneNumber(phoneNumber);
        patronRepository.save(patron);
        LOGGER.info(() -> "Updated patron " + patronId);
    }

    @Override
    public Patron findPatronById(String patronId) {
        return patronRepository.findById(patronId)
                .orElseThrow(() -> new IllegalArgumentException("Patron not found: " + patronId));
    }

    @Override
    public List<String> getBorrowingHistory(String patronId) {
        return findPatronById(patronId).getBorrowingHistory();
    }
}
