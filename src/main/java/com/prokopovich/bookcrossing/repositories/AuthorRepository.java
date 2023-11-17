package com.prokopovich.bookcrossing.repositories;

import com.prokopovich.bookcrossing.entities.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AuthorRepository extends CrudRepository<Author,Integer> {
    Author save(Author author);
    Author findAuthorByName(String name);
    Author findAuthorById(Integer id);
    @Query("SELECT a FROM Author a ORDER BY a.name ASC")
    List<Author> findAllOrderByAuthorAsc();
}
