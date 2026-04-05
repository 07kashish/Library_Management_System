package com.airtribe.librarymanagementsystem.repository.inmemory;

import com.airtribe.librarymanagementsystem.domain.Patron;
import com.airtribe.librarymanagementsystem.repository.PatronRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryPatronRepository implements PatronRepository {
    private final Map<String, Patron> patrons = new ConcurrentHashMap<>();

    @Override
    public void save(Patron patron) {
        patrons.put(patron.getPatronId(), patron);
    }

    @Override
    public Optional<Patron> findById(String patronId) {
        return Optional.ofNullable(patrons.get(patronId));
    }

    @Override
    public List<Patron> findAll() {
        return new ArrayList<>(patrons.values());
    }
}
