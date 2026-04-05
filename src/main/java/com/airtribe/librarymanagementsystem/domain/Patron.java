package com.airtribe.librarymanagementsystem.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Patron {
    private final String patronId;
    private String name;
    private String email;
    private String phoneNumber;
    private final List<String> borrowingHistory;
    private final Set<String> preferredGenres;

    public Patron(String patronId, String name, String email, String phoneNumber, Set<String> preferredGenres) {
        this.patronId = Objects.requireNonNull(patronId, "patronId cannot be null");
        this.name = Objects.requireNonNull(name, "name cannot be null");
        this.email = Objects.requireNonNull(email, "email cannot be null");
        this.phoneNumber = Objects.requireNonNull(phoneNumber, "phoneNumber cannot be null");
        this.borrowingHistory = new ArrayList<>();
        this.preferredGenres = preferredGenres == null ? new HashSet<>() : new HashSet<>(preferredGenres);
    }

    public String getPatronId() {
        return patronId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "name cannot be null");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = Objects.requireNonNull(email, "email cannot be null");
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = Objects.requireNonNull(phoneNumber, "phoneNumber cannot be null");
    }

    public List<String> getBorrowingHistory() {
        return List.copyOf(borrowingHistory);
    }

    public void addBorrowedIsbn(String isbn) {
        borrowingHistory.add(isbn);
    }

    public Set<String> getPreferredGenres() {
        return Set.copyOf(preferredGenres);
    }

    public void addPreferredGenre(String genre) {
        if (genre != null && !genre.isBlank()) {
            preferredGenres.add(genre);
        }
    }
}
