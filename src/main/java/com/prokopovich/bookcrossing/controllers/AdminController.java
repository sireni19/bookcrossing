package com.prokopovich.bookcrossing.controllers;

import com.prokopovich.bookcrossing.entities.City;
import com.prokopovich.bookcrossing.entities.Location;
import com.prokopovich.bookcrossing.exceptions.DuplicateCityException;
import com.prokopovich.bookcrossing.exceptions.DuplicateLocationException;
import com.prokopovich.bookcrossing.services.CityService;
import com.prokopovich.bookcrossing.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/admin/actions")
public class AdminController {
    private CityService cityService;
    private LocationService locationService;

    @Autowired
    public AdminController(CityService cityService, LocationService locationService) {
        this.cityService = cityService;
        this.locationService = locationService;
    }


    @GetMapping("")
    public String showActionsPage() {
        return "settings/full-functional";
    }

    @GetMapping("/cities")
    public String showCities(Model model) {
        List<City> cityList = cityService.findAllCities();
        model.addAttribute("list", cityList);
        return "settings/cities";
    }

    @PostMapping("/cities/newcity")
    public String addCity(@RequestParam("name") String name, Model model) {
        try {
            City city = new City(name);
            cityService.addCity(city);
            return "redirect:/admin/actions/cities";
        } catch (DuplicateCityException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "settings/error";
        }
    }

    @PostMapping("/cities/update")
    public String updateCity(@RequestParam("newname") String newName, @RequestParam("id") Integer id) {
        cityService.modifyCity(newName, id);
        return "redirect:/admin/actions/cities";
    }


    @GetMapping("/locations")
    public String showLocations(Model model) {
        List<City> cityList = cityService.findAllCities();
        Map<String, List<String>> cityAddressMap = cityList.stream()
                .collect(Collectors.toMap( //Returns a Collector that accumulates elements into a Map whose keys and values are the result of applying the provided mapping functions to the input elements.
                        City::getName,  // key - name of city
                        city -> city.getLocationList().stream()
                                .map(Location::getAddress)  // value - list of addresses(List<Strings>)
                                .collect(Collectors.toList()) // the address list is going to the new list.
                ));
        
        model.addAttribute("addressesInCity", cityAddressMap);
        return "settings/locations";
    }

    @PostMapping("/locations")
    public String addLocation(@RequestParam(name = "newAddress") String newAddress, @RequestParam(name = "cityName") String cityName, Model model) {
        Location location = new Location(newAddress);
        City city = cityService.findCityByName(cityName);
        if (city != null) {
            location.setCity(city);
            try {
                locationService.addLocation(location);
            } catch (DuplicateLocationException e) {
                model.addAttribute("errorMessage", e.getMessage());
                return "settings/error";
            }
        }
        return "redirect:/admin/actions/locations";
    }

    @PostMapping("/locations/update")
    public String updateLocation(@RequestParam(name = "updatedAddress") String updatedAddress, @RequestParam(name = "oldAddress") String oldAddress) throws DuplicateLocationException {
        Location location = locationService.findLocationByAddress(oldAddress);
        location.setAddress(updatedAddress);
        locationService.addLocation(location);
        return "redirect:/admin/actions/locations";
    }

    @PostMapping("/locations/delete")
    public String deleteLocation(@RequestParam(name = "addressToDelete") String address){
        locationService.deleteLocationByAddress(address);
        return "redirect:/admin/actions/locations";
    }
}
