package com.airtribe.librarymanagementsystem.domain;

import java.util.Objects;

public class LibraryBranch {
    private final String branchId;
    private String name;
    private String address;

    public LibraryBranch(String branchId, String name, String address) {
        this.branchId = Objects.requireNonNull(branchId, "branchId cannot be null");
        this.name = Objects.requireNonNull(name, "name cannot be null");
        this.address = Objects.requireNonNull(address, "address cannot be null");
    }

    public String getBranchId() {
        return branchId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
