package com.airtribe.librarymanagementsystem.service;

import com.airtribe.librarymanagementsystem.domain.Book;
import java.util.List;

public interface RecommendationService {
    List<Book> recommendBooks(String patronId);
}
