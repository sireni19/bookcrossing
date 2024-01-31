package com.prokopovich.bookcrossing.services;

import com.prokopovich.bookcrossing.dto.BookDto;
import com.prokopovich.bookcrossing.entities.Book;
import com.prokopovich.bookcrossing.entities.Location;
import com.prokopovich.bookcrossing.exceptions.DuplicateBookException;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {
    void addBook(Book book,String author,String subgenre,MultipartFile image, HttpSession session) throws DuplicateBookException;

    Book findBookByTitleIsbnAndLocation(String title, String isbn, Location location);

    void setUserToBook(String username, Integer bookId);
    Book findBookByUserEmail(String email);
    void clearUserSetNewLocationToBook(Location location, Integer bookId);

    Page<Book> findAllBooksInLocation(Location location, Pageable pageable);

    Book findBookById(Integer id);
    void updateBook(Book updatedBook, Integer bookId, MultipartFile image, String authorId, String subgenreId, HttpSession session);
    void deleteBookById(Integer id);

     Page<BookDto> getAllBooks(Pageable pageable);

    Page<BookDto> getAllBookInCity(String city, Pageable pageable);
    Page<BookDto> getAllBooksWithTitleLike(String name,Pageable pageable);

}
