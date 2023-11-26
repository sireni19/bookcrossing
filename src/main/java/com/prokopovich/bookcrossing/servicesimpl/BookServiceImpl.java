package com.prokopovich.bookcrossing.servicesimpl;

import com.prokopovich.bookcrossing.entities.Book;
import com.prokopovich.bookcrossing.entities.Location;

import com.prokopovich.bookcrossing.entities.User;
import com.prokopovich.bookcrossing.exceptions.DuplicateBookException;
import com.prokopovich.bookcrossing.repositories.BookRepository;
import com.prokopovich.bookcrossing.repositories.UserRepository;
import com.prokopovich.bookcrossing.services.BookService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;
    private UserRepository userRepository;

    @Override
    @Transactional
    public void addBook(Book book) throws DuplicateBookException {//может его сделать boolean чтобы в зависимости от флага вызывался разный view?
        String title = book.getTitle();
        String isbn = book.getIsbn();
        Location location = book.getLocation();// локация дается в книгу из залогиненного хоста
        if (bookRepository.existsBookByTitleAndIsbnAndLocation(title, isbn, location) == false) {
            bookRepository.save(book);
        } else {
            throw new DuplicateBookException("Such book is already existed");
        }
    }

    @Override
    public Book findBookByTitleIsbnAndLocation(String title, String isbn, Location location) {
        Book book = bookRepository.findByTitleIsbnAndLocation(title, isbn, location);
        return book;
    }


    @Override
    @Transactional
    public void setUserToBook(String username, Integer bookId) throws EntityNotFoundException {
        Optional<User> optional = userRepository.findUserByUsername(username);
        User user = optional.orElseThrow(() -> new EntityNotFoundException("User not found with username:: " + username));
        bookRepository.updateBookUserById(user, bookId);
    }

    @Override
    public Book findBookByUserEmail(String email) throws EntityNotFoundException{
        Optional<User> optional = userRepository.findUserByEmail(email);
        User user = optional.orElseThrow(() -> new EntityNotFoundException("User not found with username:: " + email));
        Optional<Book> optional2=bookRepository.findBookByUser(user);
        Book book =optional2.orElseThrow(()->new EntityNotFoundException("User has not book"));
        return book;
    }

    @Override
    @Transactional
    public void clearUserSetNewLocationToBook(Location newLocation, Integer bookId) {
        bookRepository.removeUserAndSetLocation(newLocation,bookId);
    }

    @Override
    public Page<Book> findAllBooksInLocation(Location location, Pageable pageable) {
        Optional<Page<Book>> optional =bookRepository.findAllByLocationAndUserIsNull(location,pageable);
        Page<Book> page =optional.orElseThrow(()->new EntityNotFoundException("No books in your location, add new books"));
        return page;
    }

    @Override
    public Book findBookById(Integer id) throws  EntityNotFoundException{
        Optional<Book> optional = bookRepository.findById(id);
        Book book = optional.orElseThrow(()->new EntityNotFoundException("Incorrect book id"));
        return book;
    }

    @Override
    @Transactional
    public void updateBook(Book book) {
        bookRepository.updateBook(book.getId(),book.getTitle(),book.getAuthor(),book.getImage());
    }

    @Override
    public void deleteBookById(Integer id) {
        bookRepository.deleteById(id);
    }
}
