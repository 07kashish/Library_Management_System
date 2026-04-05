package com.airtribe.librarymanagementsystem.repository.inmemory;

import com.airtribe.librarymanagementsystem.domain.LibraryBranch;
import com.airtribe.librarymanagementsystem.repository.BranchRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryBranchRepository implements BranchRepository {
    private final Map<String, LibraryBranch> branches = new ConcurrentHashMap<>();

    @Override
    public void save(LibraryBranch branch) {
        branches.put(branch.getBranchId(), branch);
    }

    @Override
    public Optional<LibraryBranch> findById(String branchId) {
        return Optional.ofNullable(branches.get(branchId));
    }

    @Override
    public List<LibraryBranch> findAll() {
        return new ArrayList<>(branches.values());
    }
}
