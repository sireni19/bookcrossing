package com.prokopovich.bookcrossing.servicesimpl;

import com.prokopovich.bookcrossing.entities.City;
import com.prokopovich.bookcrossing.exceptions.DuplicateCityException;
import com.prokopovich.bookcrossing.repositories.CityRepository;
import com.prokopovich.bookcrossing.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    private CityRepository cityRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public List<City> findAllCities() {
        return (List<City>) cityRepository.findAll();
    }

    @Override
    public void addCity(City city) throws DuplicateCityException {
        if(cityRepository.findCityByName(city.getName())==null){
            cityRepository.save(city);
        }else {
            throw new DuplicateCityException("City "+ city.getName() +" has been already exist");
        }
    }

    @Override
    public City findCityByName(String name) {
        return cityRepository.findCityByName(name);
    }


    @Override
    public void modifyCity(String newName, Integer id) {
        City city =cityRepository.findCityById(id);
        if(city!=null){
            city.setName(newName);
            cityRepository.save(city);
        }
    }
}
