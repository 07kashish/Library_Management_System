package com.airtribe.librarymanagementsystem.recommendation;

import com.airtribe.librarymanagementsystem.domain.Book;
import com.airtribe.librarymanagementsystem.domain.BookStatus;
import com.airtribe.librarymanagementsystem.domain.Patron;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PopularByHistoryRecommendationStrategy implements RecommendationStrategy {
    @Override
    public List<Book> recommend(Patron patron, List<Book> books) {
        Map<String, Long> isbnFrequency = patron.getBorrowingHistory().stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Set<String> preferredGenres = patron.getPreferredGenres();

        return books.stream()
                .filter(book -> book.getStatus() == BookStatus.AVAILABLE)
                .filter(book -> !patron.getBorrowingHistory().contains(book.getIsbn()))
                .sorted(Comparator
                        .comparing((Book book) -> preferredGenres.contains(book.getGenre()) ? 0 : 1)
                        .thenComparing(book -> -isbnFrequency.getOrDefault(book.getIsbn(), 0L))
                        .thenComparing(Book::getTitle))
                .limit(5)
                .toList();
    }
}
