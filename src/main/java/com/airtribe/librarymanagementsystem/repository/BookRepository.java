package com.airtribe.librarymanagementsystem.repository;

import com.airtribe.librarymanagementsystem.domain.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository {
    void save(Book book);
    Optional<Book> findById(String bookId);
    List<Book> findAll();
    void deleteById(String bookId);
}
