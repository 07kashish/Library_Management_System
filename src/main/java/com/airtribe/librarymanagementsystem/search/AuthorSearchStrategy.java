package com.airtribe.librarymanagementsystem.search;

import com.airtribe.librarymanagementsystem.domain.Book;
import java.util.List;

public class AuthorSearchStrategy implements BookSearchStrategy {
    @Override
    public List<Book> search(List<Book> books, String query) {
        String normalizedQuery = query.toLowerCase();
        return books.stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(normalizedQuery))
                .toList();
    }
}
