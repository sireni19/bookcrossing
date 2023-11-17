package com.prokopovich.bookcrossing.repositories;

import com.prokopovich.bookcrossing.entities.Book;
import com.prokopovich.bookcrossing.entities.Location;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book,Integer> {
    Book save(Book book);
    boolean existsBookByTitleAndIsbnAndLocation(String title, String number, Location location);

}
