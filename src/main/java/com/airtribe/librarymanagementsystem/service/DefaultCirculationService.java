package com.airtribe.librarymanagementsystem.service;

import com.airtribe.librarymanagementsystem.domain.Book;
import com.airtribe.librarymanagementsystem.domain.BookStatus;
import com.airtribe.librarymanagementsystem.domain.LoanRecord;
import com.airtribe.librarymanagementsystem.domain.Patron;
import com.airtribe.librarymanagementsystem.domain.Reservation;
import com.airtribe.librarymanagementsystem.domain.ReservationStatus;
import com.airtribe.librarymanagementsystem.notification.PatronNotificationObserver;
import com.airtribe.librarymanagementsystem.notification.ReservationNotificationSubject;
import com.airtribe.librarymanagementsystem.repository.BookRepository;
import com.airtribe.librarymanagementsystem.repository.LoanRepository;
import com.airtribe.librarymanagementsystem.repository.PatronRepository;
import com.airtribe.librarymanagementsystem.repository.ReservationRepository;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class DefaultCirculationService implements CirculationService {
    private static final Logger LOGGER = Logger.getLogger(DefaultCirculationService.class.getName());

    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;
    private final LoanRepository loanRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationNotificationSubject notificationSubject;

    public DefaultCirculationService(
            BookRepository bookRepository,
            PatronRepository patronRepository,
            LoanRepository loanRepository,
            ReservationRepository reservationRepository,
            ReservationNotificationSubject notificationSubject) {
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
        this.loanRepository = loanRepository;
        this.reservationRepository = reservationRepository;
        this.notificationSubject = notificationSubject;
    }

    @Override
    public LoanRecord checkoutBook(String bookId, String patronId, int loanDays) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found: " + bookId));
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new IllegalArgumentException("Patron not found: " + patronId));

        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new IllegalStateException("Book is not available for checkout");
        }

        LoanRecord loanRecord = new LoanRecord(
                UUID.randomUUID().toString(),
                bookId,
                patronId,
                LocalDate.now(),
                LocalDate.now().plusDays(loanDays));

        book.setStatus(BookStatus.BORROWED);
        patron.addBorrowedIsbn(book.getIsbn());

        bookRepository.save(book);
        patronRepository.save(patron);
        loanRepository.save(loanRecord);

        LOGGER.info(() -> "Checked out book " + bookId + " to patron " + patronId);
        return loanRecord;
    }

    @Override
    public void returnBook(String bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found: " + bookId));

        LoanRecord activeLoan = loanRepository.findAll().stream()
                .filter(loan -> loan.getBookId().equals(bookId))
                .filter(loan -> !loan.isReturned())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No active loan found for book: " + bookId));

        activeLoan.markReturned(LocalDate.now());
        loanRepository.save(activeLoan);
        book.setStatus(BookStatus.AVAILABLE);
        bookRepository.save(book);

        LOGGER.info(() -> "Returned book " + bookId);
        notifyNextReservation(book);
    }

    @Override
    public Reservation reserveBook(String patronId, String isbn, String branchId) {
        patronRepository.findById(patronId)
                .orElseThrow(() -> new IllegalArgumentException("Patron not found: " + patronId));

        Reservation reservation = new Reservation(UUID.randomUUID().toString(), patronId, isbn, branchId);
        reservationRepository.save(reservation);
        notificationSubject.register(new PatronNotificationObserver(patronId, isbn, branchId));
        LOGGER.info(() -> "Created reservation " + reservation.getReservationId() + " for patron " + patronId);
        return reservation;
    }

    @Override
    public List<LoanRecord> getLoanHistory() {
        return loanRepository.findAll();
    }

    private void notifyNextReservation(Book book) {
        List<Reservation> matchingReservations = reservationRepository.findAll().stream()
                .filter(reservation -> reservation.getIsbn().equals(book.getIsbn()))
                .filter(reservation -> reservation.getBranchId().equals(book.getBranchId()))
                .filter(reservation -> reservation.getStatus() == ReservationStatus.WAITING)
                .sorted(Comparator.comparing(Reservation::getCreatedAt))
                .toList();

        if (matchingReservations.isEmpty()) {
            return;
        }

        Reservation nextReservation = matchingReservations.get(0);
        nextReservation.setStatus(ReservationStatus.NOTIFIED);
        reservationRepository.save(nextReservation);
        book.setStatus(BookStatus.RESERVED);
        bookRepository.save(book);

        notificationSubject.notifyReservationAvailable(
                nextReservation.getPatronId(),
                nextReservation.getIsbn(),
                nextReservation.getBranchId(),
                "Reserved book with ISBN " + book.getIsbn() + " is now available at branch " + book.getBranchId());

        notificationSubject.unregister(
                nextReservation.getPatronId(),
                nextReservation.getIsbn(),
                nextReservation.getBranchId());
    }
}
