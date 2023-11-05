package com.prokopovich.bookcrossing.repositories;

import com.prokopovich.bookcrossing.entities.City;

import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, Integer> {
    City findCityById(Integer id);
    City findCityByName(String name);
}
