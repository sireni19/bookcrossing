package com.prokopovich.bookcrossing.services;

import com.prokopovich.bookcrossing.entities.Book;
import com.prokopovich.bookcrossing.exceptions.DuplicateBookException;

public interface BookService {
    void addBook(Book book) throws DuplicateBookException;
    Book findBookByISBN(String isbn);


}
