package com.prokopovich.bookcrossing.controllers;

import com.prokopovich.bookcrossing.entities.City;
import com.prokopovich.bookcrossing.exceprions.DuplicateCityException;
import com.prokopovich.bookcrossing.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/admin/actions")
public class AdminController {
    private CityService cityService;

    @Autowired
    public AdminController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("")
    public String showActionsPage() {
        return "settings/full-functional";
    }
    @GetMapping("/cities")
    public String showCities(Model model){
        List<City> cityList = cityService.findAllCities();
        model.addAttribute("list",cityList);
        return "settings/cities";
    }

    @PostMapping("/cities/newcity")
    public String addCity(@RequestParam("name") String name, Model model) {
        try {
            City city =new City(name);
            cityService.addCity(city);
            return "redirect:/admin/actions/cities";
        } catch (DuplicateCityException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "admin/error";
        }
    }
    @PostMapping("/cities/update")
    public String updateCity(@RequestParam("newname") String newName,@RequestParam("id") Integer id){
        cityService.modifyCity(newName, id);
        return "redirect:/admin/actions/cities";
    }
}
