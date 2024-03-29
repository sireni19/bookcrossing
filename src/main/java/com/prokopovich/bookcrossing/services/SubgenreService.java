package com.prokopovich.bookcrossing.services;

import com.prokopovich.bookcrossing.entities.Subgenre;

import java.util.List;

public interface SubgenreService {
    List<Subgenre> getAllSubgenres();
    void addSubgenre(String subgenre);
    Subgenre getSubgenreById(Integer id);

}
