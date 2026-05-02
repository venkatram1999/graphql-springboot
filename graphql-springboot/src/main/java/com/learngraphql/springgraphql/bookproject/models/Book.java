package com.learngraphql.springgraphql.bookproject.models;

public class Book {
    private String id;
    private String title;
    private String authorId;

    public Book(String id, String title, String authorId) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthorId() { return authorId; }
}