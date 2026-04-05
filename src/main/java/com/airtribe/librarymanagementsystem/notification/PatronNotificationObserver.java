package com.airtribe.librarymanagementsystem.notification;

import java.util.logging.Logger;

public class PatronNotificationObserver implements LibraryEventObserver {
    private static final Logger LOGGER = Logger.getLogger(PatronNotificationObserver.class.getName());

    private final String patronId;
    private final String isbn;
    private final String branchId;

    public PatronNotificationObserver(String patronId, String isbn, String branchId) {
        this.patronId = patronId;
        this.isbn = isbn;
        this.branchId = branchId;
    }

    @Override
    public String getPatronId() {
        return patronId;
    }

    @Override
    public String getIsbn() {
        return isbn;
    }

    @Override
    public String getBranchId() {
        return branchId;
    }

    @Override
    public void update(String message) {
        LOGGER.info(() -> "Notification for patron " + patronId + ": " + message);
    }
}
