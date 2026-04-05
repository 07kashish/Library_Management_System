package com.airtribe.librarymanagementsystem.repository.inmemory;

import com.airtribe.librarymanagementsystem.domain.LoanRecord;
import com.airtribe.librarymanagementsystem.repository.LoanRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryLoanRepository implements LoanRepository {
    private final Map<String, LoanRecord> loans = new ConcurrentHashMap<>();

    @Override
    public void save(LoanRecord loanRecord) {
        loans.put(loanRecord.getLoanId(), loanRecord);
    }

    @Override
    public Optional<LoanRecord> findById(String loanId) {
        return Optional.ofNullable(loans.get(loanId));
    }

    @Override
    public List<LoanRecord> findAll() {
        return new ArrayList<>(loans.values());
    }
}
