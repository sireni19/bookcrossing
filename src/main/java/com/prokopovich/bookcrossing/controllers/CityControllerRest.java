package com.prokopovich.bookcrossing.controllers;

import com.prokopovich.bookcrossing.entities.City;
import com.prokopovich.bookcrossing.services.CityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor

public class CityControllerRest {
    private  CityService cityService;
    @GetMapping("/locs/cities")
    public List<City> getAllCities(){
        return cityService.findAllCities();
    }
}
