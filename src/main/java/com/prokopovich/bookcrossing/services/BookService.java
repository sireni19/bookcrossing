package com.prokopovich.bookcrossing.services;

import com.prokopovich.bookcrossing.entities.Book;
import com.prokopovich.bookcrossing.entities.Location;
import com.prokopovich.bookcrossing.exceptions.DuplicateBookException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    void addBook(Book book) throws DuplicateBookException;

    Book findBookByTitleIsbnAndLocation(String title, String isbn, Location location);

    void setUserToBook(String username, Integer bookId);
    Book findBookByUserEmail(String email);
    void clearUserSetNewLocationToBook(Location location, Integer bookId);

    Page<Book> findAllBooksInLocation(Location location, Pageable pageable);
    Book findBookById(Integer id);
    void updateBook(Book book);
    void deleteBookById(Integer id);
}
