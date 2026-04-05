package com.airtribe.librarymanagementsystem.repository;

import com.airtribe.librarymanagementsystem.domain.LibraryBranch;
import java.util.List;
import java.util.Optional;

public interface BranchRepository {
    void save(LibraryBranch branch);
    Optional<LibraryBranch> findById(String branchId);
    List<LibraryBranch> findAll();
}
