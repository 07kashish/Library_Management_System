package com.airtribe.librarymanagementsystem.notification;

public interface LibraryEventObserver {
    String getPatronId();
    String getIsbn();
    String getBranchId();
    void update(String message);
}
