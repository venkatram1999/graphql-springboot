package com.learngraphql.springgraphql.bookproject.controller;

import com.learngraphql.springgraphql.bookproject.models.Author;
import com.learngraphql.springgraphql.bookproject.models.Book;
import com.learngraphql.springgraphql.bookproject.service.AuthorService;
import com.learngraphql.springgraphql.bookproject.service.BookService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @QueryMapping
    public Book bookById(@Argument String id) {
        return bookService.getBookById(id);
    }

    @SchemaMapping(typeName = "Book", field = "author")
    public Author author(Book book) {
        return authorService.getAuthorById(book.getAuthorId());
    }
}