package com.prokopovich.bookcrossing.controllers;


import com.prokopovich.bookcrossing.entities.Location;
import com.prokopovich.bookcrossing.services.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@AllArgsConstructor
public class LocationControllerRest {
    private LocationService locationService;


    @GetMapping("/1")
    public List<Location> getLocationsInCity(String city) {
        return locationService.findLocationsByCity(city);
    }

    @GetMapping("/2")
    public List<Location> getAllLocations() {
        return locationService.findAllLocations();
    }

    @GetMapping("/3")
    public Location getLocationsById(Integer id) {
        return locationService.getLocationById(id);
    }
}

//    @GetMapping("/{cityId}")
//    public List<Location> getLocationsByCity(@PathVariable Long cityId) {
//        City city = cityRepository.findById(cityId).orElse(null);
//        if (city != null) {
//            return addressRepository.findByCity(city);
//        } else {
//            // Обработка ошибки, если город не найден
//            return new ArrayList<>();
//        }
//    }
