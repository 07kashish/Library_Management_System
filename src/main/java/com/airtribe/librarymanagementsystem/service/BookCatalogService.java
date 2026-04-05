package com.airtribe.librarymanagementsystem.service;

import com.airtribe.librarymanagementsystem.domain.Book;
import java.util.List;

public interface BookCatalogService {
    void addBook(Book book);
    void updateBook(String bookId, String title, String author, int publicationYear, String genre);
    void removeBook(String bookId);
    Book findBookById(String bookId);
    List<Book> searchByTitle(String title);
    List<Book> searchByAuthor(String author);
    List<Book> searchByIsbn(String isbn);
    List<Book> findAvailableBooks();
    List<Book> findBorrowedBooks();
    List<Book> findAllBooks();
}
