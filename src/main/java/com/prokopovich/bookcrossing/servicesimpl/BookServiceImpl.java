package com.prokopovich.bookcrossing.servicesimpl;

import com.prokopovich.bookcrossing.entities.Book;
import com.prokopovich.bookcrossing.entities.Location;

import com.prokopovich.bookcrossing.exceptions.DuplicateBookException;
import com.prokopovich.bookcrossing.repositories.BookRepository;
import com.prokopovich.bookcrossing.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;
    @Override
    @Transactional
    public void addBook(Book book) throws DuplicateBookException {//может его сделать boolean чтобы в зависимости от флага вызывался разный view?
        String title= book.getTitle();
        String isbn = book.getIsbn();
        Location location = book.getLocation();// локация дается в книгу из залогиненного хоста
        if(bookRepository.existsBookByTitleAndIsbnAndLocation(title,isbn,location)==false){
            bookRepository.save(book);
        }else {
            throw new DuplicateBookException("Such book is already existed");
        }

    }

    @Override
    public Book findBookByISBN(String isbn) {
        return null;
    }
}
