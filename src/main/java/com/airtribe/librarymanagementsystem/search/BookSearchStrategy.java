package com.airtribe.librarymanagementsystem.search;

import com.airtribe.librarymanagementsystem.domain.Book;
import java.util.List;

public interface BookSearchStrategy {
    List<Book> search(List<Book> books, String query);
}
