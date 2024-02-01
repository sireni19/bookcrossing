package com.prokopovich.bookcrossing.servicesimpl;

import com.prokopovich.bookcrossing.dto.BookDto;
import com.prokopovich.bookcrossing.entities.*;

import com.prokopovich.bookcrossing.exceptions.DuplicateBookException;
import com.prokopovich.bookcrossing.repositories.*;
import com.prokopovich.bookcrossing.services.AuthorService;
import com.prokopovich.bookcrossing.services.BookService;
import com.prokopovich.bookcrossing.services.SubgenreService;
import com.prokopovich.bookcrossing.utils.BookUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;
    private UserRepository userRepository;
    private CityRepository cityRepository;
    private AuthorService authorService;
    private SubgenreService subgenreService;

    @Override
    @Transactional
    public void addBook(Book book,String authorId, String subgenreId,MultipartFile image, HttpSession session) throws DuplicateBookException {//может его сделать boolean чтобы в зависимости от флага вызывался разный view?
        String title = book.getTitle();
        String isbn = book.getIsbn();
        Location location = book.getLocation();// локация дается в книгу из залогиненого хоста
        Author author1 = authorService.getAuthorById(Integer.parseInt(authorId));
        Subgenre subgenre1 = subgenreService.getSubgenreById(Integer.parseInt(subgenreId));
        book.setAuthor(author1);
        book.setSubgenre(subgenre1);
        if (!image.isEmpty()) {
            try {
                byte[] bytes = image.getBytes();
                book.setImage(bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Location bookLocation = (Location) session.getAttribute("hostLocation");
        book.setLocation(bookLocation);
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
    public Book findBookByUserEmail(String email) throws EntityNotFoundException {
        Optional<User> optional = userRepository.findUserByEmail(email);
        User user = optional.orElseThrow(() -> new EntityNotFoundException("User not found with username:: " + email));
        Book book = user.getBook();
        if(book!=null){
            return book;
        }
        return null;
    }

    @Override
    @Transactional
    public void clearUserSetNewLocationToBook(Location newLocation, Integer bookId) {
        bookRepository.removeUserAndSetLocation(newLocation, bookId);
    }

    @Override
    public Page<Book> findAllBooksInLocation(Location location, Pageable pageable) {
        Optional<Page<Book>> optional = bookRepository.findAllByLocationAndUserIsNull(location, pageable);
        Page<Book> page = optional.orElseThrow(() -> new EntityNotFoundException("No books in your location, add new books"));
        return page;
    }


    @Override
    public Book findBookById(Integer id) throws EntityNotFoundException {
        Optional<Book> optional = bookRepository.findById(id);
        Book book = optional.orElseThrow(() -> new EntityNotFoundException("Incorrect book id"));
        return book;
    }

    @Override
    @Transactional
    public void updateBook(Book updatedBook, Integer bookId, MultipartFile image, String authorId, String subgenreId, HttpSession session) {
        // Получить существующую книгу по bookId из базы данных
        Book existingBook = findBookById(bookId);
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        if (!image.isEmpty()) {
            try {
                byte[] bytes = image.getBytes();
                existingBook.setImage(bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Location bookLocation = (Location) session.getAttribute("hostLocation");
        Author author1 = authorService.getAuthorById(Integer.parseInt(authorId));
        Subgenre subgenre1 = subgenreService.getSubgenreById(Integer.parseInt(subgenreId));
        existingBook.setLocation(bookLocation);
        existingBook.setAuthor(author1);
        existingBook.setSubgenre(subgenre1);
        bookRepository.updateBook(existingBook.getId(), existingBook.getTitle(), existingBook.getAuthor(), existingBook.getImage());
    }

    @Override
    public void deleteBookById(Integer id) {
        bookRepository.deleteById(id);
    }

    public Page<BookDto> getAllBooks(Pageable pageable) {
        Page<Book> all = bookRepository.findAll(pageable);
        return BookUtils.pageBooksToDtoPage(all);
    }

    @Override
    public Page<BookDto> getAllBookInCity(String cityName, Pageable pageable) {
        City city = cityRepository.findCityByName(cityName);
        Page<Book> books = bookRepository.findBooksByCity(city, pageable);
        if (city != null) {
            return BookUtils.pageBooksToDtoPage(books);
        }
        return Page.empty();
    }

    @Override
    public Page<BookDto> getAllBooksWithTitleLike(String name, Pageable pageable) {
        Page<Book> books = bookRepository.findBooksByTitle(name, pageable);
        return BookUtils.pageBooksToDtoPage(books);
    }
}
