package com.airtribe.librarymanagementsystem;

import com.airtribe.librarymanagementsystem.domain.Book;
import com.airtribe.librarymanagementsystem.domain.BookStatus;
import com.airtribe.librarymanagementsystem.domain.LibraryBranch;
import com.airtribe.librarymanagementsystem.domain.Patron;
import com.airtribe.librarymanagementsystem.domain.ReservationStatus;
import com.airtribe.librarymanagementsystem.notification.ReservationNotificationSubject;
import com.airtribe.librarymanagementsystem.recommendation.PopularByHistoryRecommendationStrategy;
import com.airtribe.librarymanagementsystem.repository.BookRepository;
import com.airtribe.librarymanagementsystem.repository.BranchRepository;
import com.airtribe.librarymanagementsystem.repository.LoanRepository;
import com.airtribe.librarymanagementsystem.repository.PatronRepository;
import com.airtribe.librarymanagementsystem.repository.ReservationRepository;
import com.airtribe.librarymanagementsystem.repository.inmemory.InMemoryBookRepository;
import com.airtribe.librarymanagementsystem.repository.inmemory.InMemoryBranchRepository;
import com.airtribe.librarymanagementsystem.repository.inmemory.InMemoryLoanRepository;
import com.airtribe.librarymanagementsystem.repository.inmemory.InMemoryPatronRepository;
import com.airtribe.librarymanagementsystem.repository.inmemory.InMemoryReservationRepository;
import com.airtribe.librarymanagementsystem.service.BookCatalogService;
import com.airtribe.librarymanagementsystem.service.CirculationService;
import com.airtribe.librarymanagementsystem.service.DefaultBookCatalogService;
import com.airtribe.librarymanagementsystem.service.DefaultCirculationService;
import com.airtribe.librarymanagementsystem.service.DefaultLibraryBranchService;
import com.airtribe.librarymanagementsystem.service.DefaultPatronService;
import com.airtribe.librarymanagementsystem.service.DefaultRecommendationService;
import com.airtribe.librarymanagementsystem.service.LibraryBranchService;
import com.airtribe.librarymanagementsystem.service.PatronService;
import com.airtribe.librarymanagementsystem.service.RecommendationService;
import java.util.Set;

public final class LibraryManagementSystemVerifier {
    private LibraryManagementSystemVerifier() {
    }

    public static void main(String[] args) {
        BookRepository bookRepository = new InMemoryBookRepository();
        PatronRepository patronRepository = new InMemoryPatronRepository();
        LoanRepository loanRepository = new InMemoryLoanRepository();
        ReservationRepository reservationRepository = new InMemoryReservationRepository();
        BranchRepository branchRepository = new InMemoryBranchRepository();
        ReservationNotificationSubject notificationSubject = new ReservationNotificationSubject();

        BookCatalogService bookCatalogService = new DefaultBookCatalogService(bookRepository);
        PatronService patronService = new DefaultPatronService(patronRepository);
        LibraryBranchService branchService = new DefaultLibraryBranchService(branchRepository, bookRepository);
        CirculationService circulationService = new DefaultCirculationService(
                bookRepository, patronRepository, loanRepository, reservationRepository, notificationSubject);
        RecommendationService recommendationService = new DefaultRecommendationService(
                patronRepository, bookRepository, new PopularByHistoryRecommendationStrategy());

        branchService.addBranch(new LibraryBranch("BR-01", "Central", "Main Street"));
        branchService.addBranch(new LibraryBranch("BR-02", "East", "East Street"));

        bookCatalogService.addBook(new Book("BK-001", "Clean Code", "Robert C. Martin", "9780132350884", 2008, "Software", "BR-01"));
        bookCatalogService.addBook(new Book("BK-002", "Effective Java", "Joshua Bloch", "9780134685991", 2018, "Software", "BR-01"));
        bookCatalogService.addBook(new Book("BK-003", "Refactoring", "Martin Fowler", "9780201485677", 1999, "Software", "BR-02"));

        patronService.addPatron(new Patron("PT-001", "Aarav", "aarav@example.com", "9999999999", Set.of("Software")));
        patronService.addPatron(new Patron("PT-002", "Meera", "meera@example.com", "8888888888", Set.of("Software")));

        circulationService.checkoutBook("BK-001", "PT-001", 7);
        assertCondition(bookCatalogService.findBookById("BK-001").getStatus() == BookStatus.BORROWED,
                "Book should be marked as borrowed after checkout");

        var reservation = circulationService.reserveBook("PT-002", "9780132350884", "BR-01");
        circulationService.returnBook("BK-001");
        assertCondition(reservation.getStatus() == ReservationStatus.NOTIFIED,
                "Reservation should be notified after return");
        assertCondition(bookCatalogService.findBookById("BK-001").getStatus() == BookStatus.RESERVED,
                "Returned reserved book should move to RESERVED state");

        branchService.transferBook("BK-003", "BR-01");
        assertCondition("BR-01".equals(bookCatalogService.findBookById("BK-003").getBranchId()),
                "Transferred book should belong to the destination branch");

        assertCondition(bookCatalogService.searchByAuthor("Joshua Bloch").size() == 1,
                "Author search should return matching books");
        assertCondition(!recommendationService.recommendBooks("PT-001").isEmpty(),
                "Recommendation strategy should suggest unread available books");

        System.out.println("Manual verification passed.");
    }

    private static void assertCondition(boolean condition, String message) {
        if (!condition) {
            throw new IllegalStateException(message);
        }
    }
}
