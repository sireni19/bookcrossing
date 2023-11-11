package com.prokopovich.bookcrossing.services;

import com.prokopovich.bookcrossing.entities.City;
import com.prokopovich.bookcrossing.exceptions.DuplicateCityException;

import java.util.List;

public interface CityService {
    List<City> findAllCities();

    public void addCity(City city) throws DuplicateCityException;

    public void modifyCity(String newName, Integer id);
    City findCityByName(String name);

}
