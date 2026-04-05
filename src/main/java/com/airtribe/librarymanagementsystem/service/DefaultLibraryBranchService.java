package com.airtribe.librarymanagementsystem.service;

import com.airtribe.librarymanagementsystem.domain.Book;
import com.airtribe.librarymanagementsystem.domain.BookStatus;
import com.airtribe.librarymanagementsystem.domain.LibraryBranch;
import com.airtribe.librarymanagementsystem.repository.BookRepository;
import com.airtribe.librarymanagementsystem.repository.BranchRepository;
import java.util.List;
import java.util.logging.Logger;

public class DefaultLibraryBranchService implements LibraryBranchService {
    private static final Logger LOGGER = Logger.getLogger(DefaultLibraryBranchService.class.getName());

    private final BranchRepository branchRepository;
    private final BookRepository bookRepository;

    public DefaultLibraryBranchService(BranchRepository branchRepository, BookRepository bookRepository) {
        this.branchRepository = branchRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void addBranch(LibraryBranch branch) {
        branchRepository.save(branch);
        LOGGER.info(() -> "Added branch " + branch.getBranchId());
    }

    @Override
    public List<LibraryBranch> getAllBranches() {
        return branchRepository.findAll();
    }

    @Override
    public void transferBook(String bookId, String toBranchId) {
        branchRepository.findById(toBranchId)
                .orElseThrow(() -> new IllegalArgumentException("Destination branch not found: " + toBranchId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found: " + bookId));

        if (book.getStatus() == BookStatus.BORROWED) {
            throw new IllegalStateException("Borrowed books cannot be transferred");
        }

        book.setStatus(BookStatus.IN_TRANSFER);
        book.setBranchId(toBranchId);
        book.setStatus(BookStatus.AVAILABLE);
        bookRepository.save(book);

        LOGGER.info(() -> "Transferred book " + bookId + " to branch " + toBranchId);
    }
}
