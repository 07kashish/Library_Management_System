package com.airtribe.librarymanagementsystem.service;

import com.airtribe.librarymanagementsystem.domain.Book;
import com.airtribe.librarymanagementsystem.domain.BookStatus;
import com.airtribe.librarymanagementsystem.repository.BookRepository;
import com.airtribe.librarymanagementsystem.search.AuthorSearchStrategy;
import com.airtribe.librarymanagementsystem.search.BookSearchStrategy;
import com.airtribe.librarymanagementsystem.search.IsbnSearchStrategy;
import com.airtribe.librarymanagementsystem.search.TitleSearchStrategy;
import java.util.List;
import java.util.logging.Logger;

public class DefaultBookCatalogService implements BookCatalogService {
    private static final Logger LOGGER = Logger.getLogger(DefaultBookCatalogService.class.getName());

    private final BookRepository bookRepository;
    private final BookSearchStrategy titleSearchStrategy;
    private final BookSearchStrategy authorSearchStrategy;
    private final BookSearchStrategy isbnSearchStrategy;

    public DefaultBookCatalogService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.titleSearchStrategy = new TitleSearchStrategy();
        this.authorSearchStrategy = new AuthorSearchStrategy();
        this.isbnSearchStrategy = new IsbnSearchStrategy();
    }

    @Override
    public void addBook(Book book) {
        bookRepository.save(book);
        LOGGER.info(() -> "Added book " + book.getTitle() + " (" + book.getBookId() + ") to branch " + book.getBranchId());
    }

    @Override
    public void updateBook(String bookId, String title, String author, int publicationYear, String genre) {
        Book book = findBookById(bookId);
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublicationYear(publicationYear);
        book.setGenre(genre);
        bookRepository.save(book);
        LOGGER.info(() -> "Updated book " + bookId);
    }

    @Override
    public void removeBook(String bookId) {
        bookRepository.deleteById(bookId);
        LOGGER.info(() -> "Removed book " + bookId);
    }

    @Override
    public Book findBookById(String bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found: " + bookId));
    }

    @Override
    public List<Book> searchByTitle(String title) {
        return titleSearchStrategy.search(bookRepository.findAll(), title);
    }

    @Override
    public List<Book> searchByAuthor(String author) {
        return authorSearchStrategy.search(bookRepository.findAll(), author);
    }

    @Override
    public List<Book> searchByIsbn(String isbn) {
        return isbnSearchStrategy.search(bookRepository.findAll(), isbn);
    }

    @Override
    public List<Book> findAvailableBooks() {
        return bookRepository.findAll().stream()
                .filter(book -> book.getStatus() == BookStatus.AVAILABLE)
                .toList();
    }

    @Override
    public List<Book> findBorrowedBooks() {
        return bookRepository.findAll().stream()
                .filter(book -> book.getStatus() == BookStatus.BORROWED)
                .toList();
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }
}
