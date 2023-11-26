package com.prokopovich.bookcrossing.repositories;

import com.prokopovich.bookcrossing.entities.Subgenre;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubgenreRepository extends CrudRepository<Subgenre,Integer> {
    Subgenre save(Subgenre subgenre);

    @Query("SELECT s FROM Subgenre s ORDER BY s.name ASC")
    List<Subgenre> findAllOrderBySubgenreAsc();
    Subgenre findSubgenreById(Integer id);
}
