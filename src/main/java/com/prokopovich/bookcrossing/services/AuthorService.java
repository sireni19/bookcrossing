package com.prokopovich.bookcrossing.services;

import com.prokopovich.bookcrossing.entities.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAllAuthors();

    void addAuthor(String author);
    Author getAuthorById(Integer id);
}
