package com.airtribe.librarymanagementsystem.repository.inmemory;

import com.airtribe.librarymanagementsystem.domain.Book;
import com.airtribe.librarymanagementsystem.repository.BookRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryBookRepository implements BookRepository {
    private final Map<String, Book> books = new ConcurrentHashMap<>();

    @Override
    public void save(Book book) {
        books.put(book.getBookId(), book);
    }

    @Override
    public Optional<Book> findById(String bookId) {
        return Optional.ofNullable(books.get(bookId));
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    @Override
    public void deleteById(String bookId) {
        books.remove(bookId);
    }
}
