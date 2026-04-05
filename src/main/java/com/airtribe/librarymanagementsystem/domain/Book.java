package com.airtribe.librarymanagementsystem.domain;

import java.util.Objects;

public class Book {
    private final String bookId;
    private String title;
    private String author;
    private final String isbn;
    private int publicationYear;
    private String genre;
    private String branchId;
    private BookStatus status;

    public Book(String bookId, String title, String author, String isbn, int publicationYear, String genre, String branchId) {
        this.bookId = Objects.requireNonNull(bookId, "bookId cannot be null");
        this.title = Objects.requireNonNull(title, "title cannot be null");
        this.author = Objects.requireNonNull(author, "author cannot be null");
        this.isbn = Objects.requireNonNull(isbn, "isbn cannot be null");
        this.publicationYear = publicationYear;
        this.genre = genre == null ? "General" : genre;
        this.branchId = Objects.requireNonNull(branchId, "branchId cannot be null");
        this.status = BookStatus.AVAILABLE;
    }

    public String getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = Objects.requireNonNull(title, "title cannot be null");
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = Objects.requireNonNull(author, "author cannot be null");
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre == null ? "General" : genre;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = Objects.requireNonNull(branchId, "branchId cannot be null");
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = Objects.requireNonNull(status, "status cannot be null");
    }
}
