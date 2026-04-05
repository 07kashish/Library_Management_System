package com.airtribe.librarymanagementsystem.service;

import com.airtribe.librarymanagementsystem.domain.Book;
import com.airtribe.librarymanagementsystem.domain.Patron;
import com.airtribe.librarymanagementsystem.recommendation.RecommendationStrategy;
import com.airtribe.librarymanagementsystem.repository.BookRepository;
import com.airtribe.librarymanagementsystem.repository.PatronRepository;
import java.util.List;

public class DefaultRecommendationService implements RecommendationService {
    private final PatronRepository patronRepository;
    private final BookRepository bookRepository;
    private final RecommendationStrategy recommendationStrategy;

    public DefaultRecommendationService(
            PatronRepository patronRepository,
            BookRepository bookRepository,
            RecommendationStrategy recommendationStrategy) {
        this.patronRepository = patronRepository;
        this.bookRepository = bookRepository;
        this.recommendationStrategy = recommendationStrategy;
    }

    @Override
    public List<Book> recommendBooks(String patronId) {
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new IllegalArgumentException("Patron not found: " + patronId));
        return recommendationStrategy.recommend(patron, bookRepository.findAll());
    }
}
