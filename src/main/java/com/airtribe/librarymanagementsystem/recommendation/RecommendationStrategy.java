package com.airtribe.librarymanagementsystem.recommendation;

import com.airtribe.librarymanagementsystem.domain.Book;
import com.airtribe.librarymanagementsystem.domain.Patron;
import java.util.List;

public interface RecommendationStrategy {
    List<Book> recommend(Patron patron, List<Book> books);
}
