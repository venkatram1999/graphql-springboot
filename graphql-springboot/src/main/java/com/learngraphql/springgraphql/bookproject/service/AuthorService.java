package com.learngraphql.springgraphql.bookproject.service;

import com.learngraphql.springgraphql.bookproject.models.Author;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final List<Author> authors = List.of(
            new Author("101", "Craig Walls"),
            new Author("102", "John Smith")
    );

    public Author getAuthorById(String id) {
        return authors.stream()
                .filter(author -> author.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}