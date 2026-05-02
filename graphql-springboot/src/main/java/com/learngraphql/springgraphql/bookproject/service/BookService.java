package com.learngraphql.springgraphql.bookproject.service;

import com.learngraphql.springgraphql.bookproject.models.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final List<Book> books = List.of(
            new Book("1", "Spring in Action", "101"),
            new Book("2", "GraphQL Basics", "102")
    );

    public Book getBookById(String id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}