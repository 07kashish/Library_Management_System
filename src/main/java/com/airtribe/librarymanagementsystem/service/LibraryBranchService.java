package com.airtribe.librarymanagementsystem.service;

import com.airtribe.librarymanagementsystem.domain.LibraryBranch;
import java.util.List;

public interface LibraryBranchService {
    void addBranch(LibraryBranch branch);
    List<LibraryBranch> getAllBranches();
    void transferBook(String bookId, String toBranchId);
}
