package com.airtribe.librarymanagementsystem.search;

import com.airtribe.librarymanagementsystem.domain.Book;
import java.util.List;

public class IsbnSearchStrategy implements BookSearchStrategy {
    @Override
    public List<Book> search(List<Book> books, String query) {
        return books.stream()
                .filter(book -> book.getIsbn().equalsIgnoreCase(query))
                .toList();
    }
}
