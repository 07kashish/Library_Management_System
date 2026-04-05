package com.airtribe.librarymanagementsystem.notification;

import java.util.ArrayList;
import java.util.List;

public class ReservationNotificationSubject {
    private final List<LibraryEventObserver> observers = new ArrayList<>();

    public void register(LibraryEventObserver observer) {
        observers.add(observer);
    }

    public void unregister(String patronId, String isbn, String branchId) {
        observers.removeIf(observer ->
                observer.getPatronId().equals(patronId)
                        && observer.getIsbn().equals(isbn)
                        && observer.getBranchId().equals(branchId));
    }

    public void notifyReservationAvailable(String patronId, String isbn, String branchId, String message) {
        observers.stream()
                .filter(observer -> observer.getPatronId().equals(patronId))
                .filter(observer -> observer.getIsbn().equals(isbn))
                .filter(observer -> observer.getBranchId().equals(branchId))
                .findFirst()
                .ifPresent(observer -> observer.update(message));
    }
}
