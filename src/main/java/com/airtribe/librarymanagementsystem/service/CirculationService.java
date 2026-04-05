package com.airtribe.librarymanagementsystem.service;

import com.airtribe.librarymanagementsystem.domain.LoanRecord;
import com.airtribe.librarymanagementsystem.domain.Reservation;
import java.util.List;

public interface CirculationService {
    LoanRecord checkoutBook(String bookId, String patronId, int loanDays);
    void returnBook(String bookId);
    Reservation reserveBook(String patronId, String isbn, String branchId);
    List<LoanRecord> getLoanHistory();
}
