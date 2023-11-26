package com.prokopovich.bookcrossing.repositories;

import com.prokopovich.bookcrossing.entities.Author;
import com.prokopovich.bookcrossing.entities.Book;
import com.prokopovich.bookcrossing.entities.Location;
import com.prokopovich.bookcrossing.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Integer> {
    Book save(Book book);

    boolean existsBookByTitleAndIsbnAndLocation(String title, String number, Location location);

    @Query("SELECT b FROM Book b JOIN b.location l WHERE b.title = :title AND b.isbn = :isbn AND l = :location")
    Book findByTitleIsbnAndLocation(String title, String isbn, Location location);

    @Modifying
    @Query("UPDATE Book b SET b.user = :user WHERE b.id = :id")
    void updateBookUserById(@Param("user") User user, @Param("id") Integer id);

    @Modifying
    @Query("UPDATE Book b SET b.user = null, b.location = :newLocation WHERE b.id = :bookId")
    void removeUserAndSetLocation(@Param("newLocation") Location newLocation, @Param("bookId") Integer bookId);

    Optional<Book> findBookByUser(User user);

    Optional<Page<Book>> findAllByLocationAndUserIsNull(Location location, Pageable pageable);

   Book findBookById(Integer id);
    @Modifying
    @Query("UPDATE Book b SET b.title = :title, b.author = :author, b.image = :image WHERE b.id = :id")
    void updateBook(@Param("id") int id, @Param("title") String title, @Param("author") Author author, @Param("image") byte[] image);
}


