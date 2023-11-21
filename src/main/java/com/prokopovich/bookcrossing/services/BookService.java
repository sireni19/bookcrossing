package com.prokopovich.bookcrossing.services;

import com.prokopovich.bookcrossing.entities.Book;
import com.prokopovich.bookcrossing.entities.Location;
import com.prokopovich.bookcrossing.entities.User;
import com.prokopovich.bookcrossing.exceptions.DuplicateBookException;

public interface BookService {
    void addBook(Book book) throws DuplicateBookException;

    Book findBookByTitleIsbnAndLocation(String title, String isbn, Location location);

    void setUserToBook(String username, Integer bookId);
    Book findBookByUserEmail(String email);
    void clearUserSetNewLocationToBook(Location location, Integer bookId);
}
