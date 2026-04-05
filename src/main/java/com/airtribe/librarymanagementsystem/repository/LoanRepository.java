package com.airtribe.librarymanagementsystem.repository;

import com.airtribe.librarymanagementsystem.domain.LoanRecord;
import java.util.List;
import java.util.Optional;

public interface LoanRepository {
    void save(LoanRecord loanRecord);
    Optional<LoanRecord> findById(String loanId);
    List<LoanRecord> findAll();
}
