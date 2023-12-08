package com.prokopovich.bookcrossing.repositories;

import com.prokopovich.bookcrossing.entities.News;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface NewsRepository extends CrudRepository<News, Integer> {
    @Query("SELECT n.title FROM News n WHERE n.id = (SELECT MAX(n2.id) FROM News n2)")
    String findLatestNewsTitle();
}

